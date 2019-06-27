package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TokenizationType {
    @JsonProperty("PAYMENT_GATEWAY")
    GATEWAY,
    //@JsonProperty("DIRECT") - not supported
    //DIRECT
}
