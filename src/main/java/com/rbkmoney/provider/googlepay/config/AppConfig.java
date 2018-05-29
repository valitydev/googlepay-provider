package com.rbkmoney.provider.googlepay.config;

import com.google.crypto.tink.apps.paymentmethodtoken.GooglePaymentsPublicKeysManager;
import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;
import com.rbkmoney.damsel.payment_tool_provider.PaymentToolProviderSrv;
import com.rbkmoney.provider.googlepay.iface.decrypt.ProviderHandler;
import com.rbkmoney.provider.googlepay.service.DecryptionService;
import com.rbkmoney.provider.googlepay.service.GPKeyStore;
import com.rbkmoney.provider.googlepay.service.ValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by vpankrashkin on 29.05.18.
 */
@Configuration
public class AppConfig {

    @Bean
    public DecryptionService decryptionService(@Value("${google.test}") boolean testMode, @Value("${google.gateway_id}") String gatewayId, @Value("${google.keys_path}")Resource keys) throws GeneralSecurityException, IOException {

        PaymentMethodTokenRecipient.Builder builder = new PaymentMethodTokenRecipient.Builder();
        builder.fetchSenderVerifyingKeysWith(
                testMode ? GooglePaymentsPublicKeysManager.INSTANCE_TEST : GooglePaymentsPublicKeysManager.INSTANCE_PRODUCTION)
        .recipientId("gateway:"+gatewayId);
        for (String key: new GPKeyStore(keys.getURI()).getKeys()) {
            builder.addRecipientPrivateKey(key);
        }

        return new DecryptionService(builder.build());
    }

    @Bean
    public PaymentToolProviderSrv.Iface decryptHandler(DecryptionService decryptionService, @Value("${google.use_validation}") boolean enableValidation) {
        return new ProviderHandler(new ValidationService(), decryptionService, enableValidation);
    }
}
