package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vpankrashkin on 28.05.18.
 */
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

    public Auth3DS() {
    }

    public String getCryptogram() {
        return cryptogram;
    }

    public void setCryptogram(String cryptogram) {
        this.cryptogram = cryptogram;
    }

    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    @Override
    public String toString() {
        return "Auth3DS{" +
                "cryptogram='" + (cryptogram == null ? null : "***") + '\'' +
                ", eci='" + eci + '\'' +
                "}";
    }
}
