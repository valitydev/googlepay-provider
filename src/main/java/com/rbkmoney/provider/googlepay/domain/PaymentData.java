package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
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

}
