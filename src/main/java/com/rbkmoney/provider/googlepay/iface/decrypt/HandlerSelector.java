package com.rbkmoney.provider.googlepay.iface.decrypt;

import com.rbkmoney.damsel.base.Content;
import com.rbkmoney.damsel.base.InvalidRequest;
import com.rbkmoney.damsel.payment_tool_provider.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by vpankrashkin on 18.06.18.
 */
public class HandlerSelector implements PaymentToolProviderSrv.Iface {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final PaymentToolProviderSrv.Iface test;
    private final PaymentToolProviderSrv.Iface prod;
    private final Pattern testPattern;

    public HandlerSelector(PaymentToolProviderSrv.Iface test, PaymentToolProviderSrv.Iface prod, String testMerchantIdPattern) {
        this.test = test;
        this.prod = prod;
        this.testPattern = Pattern.compile(testMerchantIdPattern);
    }

    @Override
    public UnwrappedPaymentTool unwrap(WrappedPaymentTool payment_tool) throws InvalidRequest, TException {
        log.info("New unwrap request: {}", payment_tool);
        Content content = payment_tool.getRequest().getGoogle().getPaymentToken();
        if (!content.getType().equalsIgnoreCase("application/json")) {
            throw new InvalidRequest(Arrays.asList("Wrong content type"));
        }

        UnwrappedPaymentTool result;
        String gatewayMerchantId = payment_tool.getRequest().getGoogle().getGatewayMerchantId();
        if (testPattern.matcher(gatewayMerchantId).matches()) {
            log.info("Test mode selected for: {}", gatewayMerchantId);
            result = test.unwrap(payment_tool);
        } else {
            log.info("Prod mode selected for: {}", gatewayMerchantId);
            result = prod.unwrap(payment_tool);
        }

        UnwrappedPaymentTool logResult = new UnwrappedPaymentTool(result);
        if (logResult.getPaymentData().isSetCard()) {
            logResult.getPaymentData().setCard(new Card());
        } else {
            logResult.getPaymentData().setTokenizedCard(new TokenizedCard());
        }
        log.info("Unwrap partial result: {}", logResult);
        return result;
    }
}
