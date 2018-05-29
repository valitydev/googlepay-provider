package com.rbkmoney.provider.googlepay.service;

import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;

import java.security.GeneralSecurityException;

/**
 * Created by vpankrashkin on 29.05.18.
 */
public class DecryptionService {
    private final PaymentMethodTokenRecipient decryptor;

    public DecryptionService(PaymentMethodTokenRecipient decryptor) {
        this.decryptor = decryptor;
    }

    public String decryptToken(String paymentToken) throws CryptoException {
        try {
            return decryptor.unseal(paymentToken);
        } catch (GeneralSecurityException e) {
            throw new CryptoException("Message decryption failed", e);
        }
    }
}
