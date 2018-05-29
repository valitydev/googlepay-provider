package com.rbkmoney.provider.googlepay.iface.decrypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.rbkmoney.damsel.base.Content;
import com.rbkmoney.damsel.base.InvalidRequest;
import com.rbkmoney.damsel.domain.BankCardPaymentSystem;
import com.rbkmoney.damsel.payment_tool_provider.*;
import com.rbkmoney.geck.common.util.TypeUtil;
import com.rbkmoney.provider.googlepay.domain.DecryptedMessage;
import com.rbkmoney.provider.googlepay.domain.PaymentData;
import com.rbkmoney.provider.googlepay.domain.PaymentToken;
import com.rbkmoney.provider.googlepay.service.CryptoException;
import com.rbkmoney.provider.googlepay.service.DecryptionService;
import com.rbkmoney.provider.googlepay.service.ValidationException;
import com.rbkmoney.provider.googlepay.service.ValidationService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by vpankrashkin on 03.04.18.
 */
public class ProviderHandler implements PaymentToolProviderSrv.Iface {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ValidationService validator;
    private final DecryptionService decryptor;
    private final ObjectReader srcReader = new ObjectMapper().readerFor(PaymentData.class);
    private final ObjectReader resReader = new ObjectMapper().readerFor(DecryptedMessage.class);
    private final boolean enableValidation;

    public ProviderHandler(ValidationService validator, DecryptionService decryptor, boolean enableValidation) {
        this.validator = validator;
        this.decryptor = decryptor;
        this.enableValidation = enableValidation;
    }

    @Override
    public UnwrappedPaymentTool unwrap(WrappedPaymentTool payment_tool) throws InvalidRequest, TException {
        log.info("New unwrap request: {}", payment_tool);
        Content content = payment_tool.getRequest().getGoogle().getPaymentToken();
        if (!content.getType().equalsIgnoreCase("application/json")) {
            throw new InvalidRequest(Arrays.asList("Wrong content type"));
        }

        try {
            PaymentData paymentData = srcReader.readValue(content.getData());
            PaymentToken paymentToken = paymentData.getPaymentMethodToken();

            String tokenData = decryptor.decryptToken(paymentToken.getToken());
            log.info("Payment data decrypted");
            DecryptedMessage decryptedMessage = resReader.readValue(tokenData);
            log.info("Decryption result: {}", decryptedMessage);

            if (enableValidation) {
                validator.validate(payment_tool.getRequest().getGoogle().getGatewayMerchantId(), decryptedMessage);
                log.info("Request successfully validated");
            } else {
                log.info("Request validation skipped");
            }
            UnwrappedPaymentTool result = new UnwrappedPaymentTool();
            result.setCardInfo(extractCardInfo(paymentData.getCardInfo()));
            result.setPaymentData(extractPaymentData(decryptedMessage));
            result.setDetails(extractPaymentDetails(decryptedMessage));

            UnwrappedPaymentTool logResult = new UnwrappedPaymentTool(result);
            if (logResult.getPaymentData().isSetCard()) {
                logResult.getPaymentData().setCard(new Card());
            } else {
                logResult.getPaymentData().setTokenizedCard(new TokenizedCard());
            }
            log.info("Unwrap partial result: {}", logResult);
            return result;
        } catch (IOException e) {
            log.error("Failed to read json data: {}", filterPan(e.getMessage()));
            throw new InvalidRequest(Arrays.asList("Failed to read json data"));
        } catch (ValidationException e) {
            log.error("Failed to validate request", e);
            throw new InvalidRequest(Arrays.asList(e.getMessage()));
        } catch (CryptoException e) {
            log.error("Decryption error", e);
            throw new RuntimeException(e);
        }
    }

    private String filterPan(String src) {
        return src.replaceAll("([^\\d])\\d{8,19}([^\\d])", "$1***$2");
    }

    private CardInfo extractCardInfo(com.rbkmoney.provider.googlepay.domain.CardInfo srcCardInfo) {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setDisplayName(srcCardInfo.getCardDescription());
        cardInfo.setLast4Digits(srcCardInfo.getLast4digits());
        cardInfo.setCardClass(TypeUtil.toEnumField(
                Optional.of(srcCardInfo.getCardClass()).map(s -> s.toLowerCase()).orElse(null),
                CardClass.class, CardClass.unknown));
        cardInfo.setPaymentSystem(TypeUtil.toEnumField(
                Optional.ofNullable(srcCardInfo.getCardNetwork()).map(s -> s.toLowerCase()).orElse(null),
                BankCardPaymentSystem.class, null));
        return cardInfo;
    }

    private CardPaymentData extractPaymentData(DecryptedMessage decryptedMessage) {
        CardPaymentData cardPaymentData = new CardPaymentData();
        switch (decryptedMessage.getPaymentMethod()) {
            case CARD:
                com.rbkmoney.provider.googlepay.domain.Card srcCard = (com.rbkmoney.provider.googlepay.domain.Card) decryptedMessage.getPaymentCredential();
                Card card = new Card(srcCard.getPan(), new ExpDate((byte) srcCard.getExpirationMonth(), (short) srcCard.getExpirationYear()));
                cardPaymentData.setCard(card);
                break;
            case TOKENIZED:
                com.rbkmoney.provider.googlepay.domain.TokenizedCard srcToken = (com.rbkmoney.provider.googlepay.domain.TokenizedCard) decryptedMessage.getPaymentCredential();
                AuthData authData;
                switch (srcToken.getAuthType()) {
                    case AUTH_3DS:
                        com.rbkmoney.provider.googlepay.domain.Auth3DS srcAuth3DS = (com.rbkmoney.provider.googlepay.domain.Auth3DS) srcToken.getAuth();
                        Auth3DS auth3DS = new Auth3DS(srcAuth3DS.getCryptogram());
                        auth3DS.setEci(srcAuth3DS.getEci());
                        authData = AuthData.auth_3ds(auth3DS);
                        break;
                    default:
                        throw new ValidationException("Unknown type:" + srcToken.getAuthType());
                }
                TokenizedCard tokenizedCard = new TokenizedCard(
                        srcToken.getDpan(),
                        new ExpDate((byte) srcToken.getExpirationMonth(), (short) srcToken.getExpirationYear()),
                        authData);
                cardPaymentData.setTokenizedCard(tokenizedCard);
                break;
            default:
                throw new ValidationException("Unknown type:" + decryptedMessage.getPaymentMethod());
        }
        return cardPaymentData;
    }

    private PaymentDetails extractPaymentDetails(DecryptedMessage decryptedMessage) {
        return PaymentDetails.google(new GooglePayDetails(decryptedMessage.getMessageId(), TypeUtil.temporalToString(decryptedMessage.getExpiration())));
    }
}
