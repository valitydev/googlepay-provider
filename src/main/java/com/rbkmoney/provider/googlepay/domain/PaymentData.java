package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vpankrashkin on 28.05.18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentData {
    private CardInfo cardInfo;
    private PaymentToken paymentMethodToken;

    @JsonCreator
    public PaymentData(
            @JsonProperty(value = "paymentMethodToken", required = true) PaymentToken paymentMethodToken,
            @JsonProperty(value = "cardInfo", required = true) CardInfo cardInfo) {
        this.paymentMethodToken = paymentMethodToken;
        this.cardInfo = cardInfo;
    }

    public PaymentData() {
    }

    public PaymentToken getPaymentMethodToken() {
        return paymentMethodToken;
    }

    public void setPaymentMethodToken(PaymentToken paymentMethodToken) {
        this.paymentMethodToken = paymentMethodToken;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    @Override
    public String toString() {
        return "PaymentData{" +
                "cardInfo=" + cardInfo +
                ", paymentMethodToken=" + paymentMethodToken +
                '}';
    }
}
