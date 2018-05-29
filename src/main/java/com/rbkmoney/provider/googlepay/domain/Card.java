package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vpankrashkin on 28.05.18.
 */
public class Card extends PaymentCredential {
    private String pan;
    private int expirationMonth;
    private int expirationYear;

    @JsonCreator
    public Card(
            @JsonProperty(value = "pan", required = true) String pan,
            @JsonProperty(value = "expirationMonth", required = true) int expirationMonth,
            @JsonProperty(value = "expirationYear", required = true) int expirationYear) {
        this.pan = pan;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    public Card() {
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
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

    @Override
    public String toString() {
        return "Card{" +
                "pan='" + (pan == null ? null: "***") + '\'' +
                ", expirationMonth=" + "**" +
                ", expirationYear=" + "****" +
                '}';
    }
}
