package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
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

}
