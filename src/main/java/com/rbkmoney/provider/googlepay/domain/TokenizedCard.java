package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rbkmoney.provider.googlepay.service.TokenizedCardDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = TokenizedCardDeserializer.class)
@ToString(exclude = {"dpan", "expirationMonth", "expirationYear"})
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

}
