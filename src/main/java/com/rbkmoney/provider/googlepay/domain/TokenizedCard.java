package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rbkmoney.provider.googlepay.service.TokenizedCardDeserializer;

/**
 * Created by vpankrashkin on 28.05.18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = TokenizedCardDeserializer.class)
public class TokenizedCard extends PaymentCredential {
    private String dpan;
    private int expirationMonth;
    private int expirationYear;
    private AuthType authType;
    private Auth auth;

    @JsonCreator
    public TokenizedCard(
            @JsonProperty(value = "dpan", required = true) String dpan,
            @JsonProperty(value = "expirationMonth", required = true) int expirationMonth,
            @JsonProperty(value = "expirationYear", required = true) int expirationYear,
            @JsonProperty(value = "authMethod", required = true) AuthType authType) {
        this.dpan = dpan;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.authType = authType;
    }

    public TokenizedCard() {
    }

    public String getDpan() {
        return dpan;
    }

    public void setDpan(String dpan) {
        this.dpan = dpan;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "TokenizedCard{" +
                "dpan='" + (dpan == null ? null : "***") + '\'' +
                ", expirationMonth=" + "**" +
                ", expirationYear=" + "****" +
                ", authType=" + authType +
                ", auth=" + auth +
                "} ";
    }
}
