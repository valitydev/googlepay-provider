package com.rbkmoney.provider.googlepay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rbkmoney.provider.googlepay.service.DecryptedMessageDeserializer;

import java.time.Instant;
import java.util.Map;

/**
 * Created by vpankrashkin on 28.05.18.
 */
@JsonDeserialize(using = DecryptedMessageDeserializer.class)
public class DecryptedMessage {
    private Instant expiration;
    private String messageId;
    private String gatewayMerchantId;
    private PaymentMethod paymentMethod;
    private PaymentCredential paymentCredential;
    private Map<String, Object> paymentCredentialMap;


    public DecryptedMessage() {
    }

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

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getGatewayMerchantId() {
        return gatewayMerchantId;
    }

    public void setGatewayMerchantId(String gatewayMerchantId) {
        this.gatewayMerchantId = gatewayMerchantId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentCredential getPaymentCredential() {
        return paymentCredential;
    }

    public void setPaymentCredential(PaymentCredential paymentCredential) {
        this.paymentCredential = paymentCredential;
    }

    public Map<String, Object> getPaymentCredentialMap() {
        return paymentCredentialMap;
    }

    public void setPaymentCredentialMap(Map<String, Object> paymentCredentialMap) {
        this.paymentCredentialMap = paymentCredentialMap;
    }

    @Override
    public String toString() {
        return "DecryptedMessage{" +
                "expiration=" + expiration +
                ", messageId='" + messageId + '\'' +
                ", gatewayMerchantId='" + gatewayMerchantId + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", paymentCredential=" + paymentCredential +
                ", paymentCredentialMap=" + paymentCredentialMap.size() +
                '}';
    }
}
