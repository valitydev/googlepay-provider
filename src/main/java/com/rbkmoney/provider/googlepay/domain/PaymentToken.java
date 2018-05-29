package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vpankrashkin on 28.05.18.
 */
public class PaymentToken {
    private String token;
    private TokenizationType tokenizationType;

    @JsonCreator
    public PaymentToken(
            @JsonProperty(value = "tokenizationType", required = true) TokenizationType tokenizationType,
            @JsonProperty(value = "token", required = true) String token) {
        this.tokenizationType = tokenizationType;
        this.token = token;
    }

    public PaymentToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenizationType getTokenizationType() {
        return tokenizationType;
    }

    public void setTokenizationType(TokenizationType tokenizationType) {
        this.tokenizationType = tokenizationType;
    }

    @Override
    public String toString() {
        return "PaymentToken{" +
                "token='" + token + '\'' +
                ", tokenizationType=" + tokenizationType +
                '}';
    }
}
