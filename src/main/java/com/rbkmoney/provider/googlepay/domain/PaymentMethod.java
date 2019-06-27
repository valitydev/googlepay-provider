package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentMethod {
    @JsonProperty("TOKENIZED_CARD")
    TOKENIZED,
    @JsonProperty("CARD")
    CARD
}
