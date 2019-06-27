package com.rbkmoney.provider.googlepay.config;

import com.google.crypto.tink.apps.paymentmethodtoken.GooglePaymentsPublicKeysManager;
import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;
import com.rbkmoney.damsel.payment_tool_provider.PaymentToolProviderSrv;
import com.rbkmoney.provider.googlepay.iface.decrypt.HandlerSelector;
import com.rbkmoney.provider.googlepay.iface.decrypt.ProviderHandler;
import com.rbkmoney.provider.googlepay.service.DecryptionService;
import com.rbkmoney.provider.googlepay.service.GPKeyStore;
import com.rbkmoney.provider.googlepay.service.ValidationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class AppConfig {

    @Bean
    public DecryptionService testDecryptionService(@Value("${google.gateway_id}") String gatewayId, @Value("${google.test_keys_path}") Resource keys) throws GeneralSecurityException, IOException {

        PaymentMethodTokenRecipient.Builder builder = new PaymentMethodTokenRecipient.Builder();
        builder.fetchSenderVerifyingKeysWith(GooglePaymentsPublicKeysManager.INSTANCE_TEST)
                .recipientId("gateway:" + gatewayId);
        for (String key : new GPKeyStore(keys.getURI()).getKeys()) {
            builder.addRecipientPrivateKey(key);
        }
        return new DecryptionService(builder.build());
    }

    @Bean
    public DecryptionService prodDecryptionService(@Value("${google.gateway_id}") String gatewayId, @Value("${google.prod_keys_path}") Resource keys) throws GeneralSecurityException, IOException {

        PaymentMethodTokenRecipient.Builder builder = new PaymentMethodTokenRecipient.Builder();
        builder.fetchSenderVerifyingKeysWith(GooglePaymentsPublicKeysManager.INSTANCE_PRODUCTION)
                .recipientId("gateway:" + gatewayId);
        for (String key : new GPKeyStore(keys.getURI()).getKeys()) {
            builder.addRecipientPrivateKey(key);
        }
        return new DecryptionService(builder.build());
    }

    @Bean
    public PaymentToolProviderSrv.Iface testDecryptHandler(@Qualifier("testDecryptionService") DecryptionService decryptionService, @Value("${google.use_validation}") boolean enableValidation) {
        return new ProviderHandler(new ValidationService(), decryptionService, enableValidation);
    }

    @Bean
    public PaymentToolProviderSrv.Iface prodDecryptHandler(@Qualifier("prodDecryptionService") DecryptionService decryptionService, @Value("${google.use_validation}") boolean enableValidation) {
        return new ProviderHandler(new ValidationService(), decryptionService, enableValidation);
    }

    @Bean
    public PaymentToolProviderSrv.Iface decryptHandler(@Qualifier("testDecryptHandler") PaymentToolProviderSrv.Iface testHandler, @Qualifier("prodDecryptHandler") PaymentToolProviderSrv.Iface prodHandler, @Value("${google.test_merchant_id_pattern}") String testPatternStr) {
        return new HandlerSelector(testHandler, prodHandler, testPatternStr);
    }
}
