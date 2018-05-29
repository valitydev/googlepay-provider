package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vpankrashkin on 28.05.18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardInfo {
    private String cardDescription;
    private String cardClass;
    private String last4digits;
    private String cardNetwork;

    @JsonCreator
    public CardInfo(
            @JsonProperty(value = "cardDescription", required = true) String cardDescription,
            @JsonProperty(value = "cardClass", required = true) String cardClass,
            @JsonProperty(value = "cardDetails", required = true) String last4digits,
        @JsonProperty(value = "cardNetwork", required = true) String cardNetwork) {
        this.cardDescription = cardDescription;
        this.cardClass = cardClass;
        this.last4digits = last4digits;
        this.cardNetwork = cardNetwork;
    }

    public CardInfo() {
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public String getCardNetwork() {
        return cardNetwork;
    }

    public void setCardNetwork(String cardNetwork) {
        this.cardNetwork = cardNetwork;
    }

    public String getCardClass() {
        return cardClass;
    }

    public void setCardClass(String cardClass) {
        this.cardClass = cardClass;
    }

    public String getLast4digits() {
        return last4digits;
    }

    public void setLast4digits(String last4digits) {
        this.last4digits = last4digits;
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "cardDescription='" + cardDescription + '\'' +
                ", cardClass='" + cardClass + '\'' +
                ", last4digits='" + last4digits + '\'' +
                ", cardNetwork='" + cardNetwork + '\'' +
                '}';
    }
}
