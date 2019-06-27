package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString(exclude = "cryptogram")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Auth3DS extends Auth {

    private String cryptogram;
    private String eci;

    @JsonCreator
    public Auth3DS(
            @JsonProperty(value = "3dsCryptogram", required = true) String cryptogram,
            @JsonProperty(value = "3dsEciIndicator") String eci) {
        this.cryptogram = cryptogram;
        this.eci = eci;
    }

}
