package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rbkmoney.provider.googlepay.service.DecryptedMessageDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonDeserialize(using = DecryptedMessageDeserializer.class)
public class DecryptedMessage {

    private Instant expiration;
    private String messageId;
    private String gatewayMerchantId;
    private PaymentMethod paymentMethod;
    private PaymentCredential paymentCredential;
    @ToString.Exclude
    private Map<String, Object> paymentCredentialMap;

    @JsonCreator
    public DecryptedMessage(
            @JsonProperty(value = "messageExpiration", required = true) Instant expiration,
            @JsonProperty(value = "messageId", required = true) String messageId,
            @JsonProperty(value = "gatewayMerchantId", required = true) String gatewayMerchantId,
            @JsonProperty(value = "paymentMethod", required = true) PaymentMethod paymentMethod,
            @JsonProperty(value = "paymentMethodDetails", required = true) Map<String, Object> paymentCredentialMap) {
        this.expiration = expiration;
        this.messageId = messageId;
        this.gatewayMerchantId = gatewayMerchantId;
        this.paymentMethod = paymentMethod;
        this.paymentCredentialMap = paymentCredentialMap;
    }

}
