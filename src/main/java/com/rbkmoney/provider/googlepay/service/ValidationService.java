package com.rbkmoney.provider.googlepay.service;

import com.rbkmoney.provider.googlepay.domain.DecryptedMessage;

import java.time.Instant;

public class ValidationService {
    public void validate(String gMerchantId, DecryptedMessage message) throws ValidationException {
        if (!gMerchantId.equals(message.getGatewayMerchantId())) {
            throw new ValidationException(String.format("Merchant ID: '%s' doesn't match decrypted one: '%s'", gMerchantId, message.getGatewayMerchantId()));
        }

        if (Instant.now().isAfter(message.getExpiration())) {
            throw new ValidationException("Message expired: " + message.getExpiration());
        }
    }
}
