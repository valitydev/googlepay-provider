package com.rbkmoney.provider.googlepay.service;

import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;
import lombok.RequiredArgsConstructor;

import java.security.GeneralSecurityException;

@RequiredArgsConstructor
public class DecryptionService {

    private final PaymentMethodTokenRecipient decryptor;

    public String decryptToken(String paymentToken) throws CryptoException {
        try {
            return decryptor.unseal(paymentToken);
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            throw new CryptoException("Message decryption failed: " + e.getMessage(), e);
        }
    }
}
