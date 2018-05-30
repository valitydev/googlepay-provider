package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vpankrashkin on 28.05.18.
 */
public enum PaymentMethod {
    @JsonProperty("TOKENIZED_CARD")
    TOKENIZED,
    @JsonProperty("CARD")
    CARD
}
