package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "Card{" +
                "pan='" + (pan == null ? null : "***") + '\'' +
                ", expirationMonth=" + "**" +
                ", expirationYear=" + "****" +
                '}';
    }

}
