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

}
