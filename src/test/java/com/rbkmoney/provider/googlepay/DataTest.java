package com.rbkmoney.provider.googlepay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.crypto.tink.apps.paymentmethodtoken.GooglePaymentsPublicKeysManager;
import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;
import com.rbkmoney.provider.googlepay.domain.DecryptedMessage;
import com.rbkmoney.provider.googlepay.domain.PaymentData;
import com.rbkmoney.provider.googlepay.service.GPKeyStore;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

public class DataTest {

    static {
        GooglePaymentsPublicKeysManager.INSTANCE_TEST.refreshInBackground();
    }

    @org.junit.Test(expected = GeneralSecurityException.class)
    public void test() throws GeneralSecurityException, IOException, URISyntaxException {
        String paymentMethodToken = "{\"signature\":\"MEUCIQDiT2/dI+H7LiiP0Yu/p8Z62Laumnk/js9cQrTUpYufIAIgN+e3tDoBDnqk6pt+MEwsVWD3oq/DfbbcLPJgdo3AMfM\\u003d\",\"protocolVersion\":\"ECv1\",\"signedMessage\":\"{\\\"encryptedMessage\\\":\\\"k84ZAjkWfAibZmWxJfBJ8+3lhNiVvPrMYvLsZQwSt5dEmvO1S7davQ5rq04MNLKjwRezz301L6kDxv6abe2M7Z2KIM/tlmgtsAlV2xbOxS2piChvEeB0RyVbAxjLKfL5vnOlxivLgoPbCUrm7/hSwxsPhjVvyTXMCBbo744ZnIINuk8W3aROtFnGBqdzWSEjmlrhUBHoTJ1IEMuowoklUyI2Qd8pCQHA4it8mvITPE/KDQNt4J4HmNAx1GkW/Va9SEHZXP/J5xNjFeijshDZgd9oam1JB2yg3sgIh3e2JmgkVChqHoilaEdjKTLJc95FlJfbZyxytmh4E7jxvJ//vB5ySGV1pyVVRT8IX6K2oIXjsJ+JAZQmr7/MNo7W/cq6guz07nFHMqk8FCJbsWA75krj4Y9Ls8QkDyI7ccayapE2N9/QP5koLglASxWFfUEgWyWPWA\\\\u003d\\\\u003d\\\",\\\"ephemeralPublicKey\\\":\\\"BLhY0C4bLP+Cv6AKH7Hz2YKICO2QqFZYxFDOWZ07S0LfEAwCmiusTIW/gz377AveU3Fw5JNKZPNjHkBG4OkEnKk\\\\u003d\\\",\\\"tag\\\":\\\"JvdgTyndj5fDugbG3rEoN52adgA/x24aGmRAKyoqxrk\\\\u003d\\\"}\"}";
        PaymentMethodTokenRecipient.Builder builder = new PaymentMethodTokenRecipient.Builder();
        builder.fetchSenderVerifyingKeysWith(
                GooglePaymentsPublicKeysManager.INSTANCE_TEST)
                .recipientId("gateway:rbkmoney");
        for (String key: new GPKeyStore(this.getClass().getClassLoader().getResource("test_keys.txt").toURI()).getKeys()) {
            builder.addRecipientPrivateKey(key);
        }
        String decryptedMessage = builder.build()
                        .unseal(paymentMethodToken);
        System.out.println(decryptedMessage);
    }

    @org.junit.Test
    public void testDeserializationEncryptedMsg() throws IOException {
        String encCard = "{\n" +
                "  \"cardInfo\": {\n" +
                "    \"cardNetwork\": \"VISA\",\n" +
                "    \"cardDetails\": \"9391\",\n" +
                "    \"cardImageUri\": \"https://lh6.ggpht.com/NvYf_33MleY1waJfW6O98wb3KU6XeinwiahmvUIyu46LcWeQdTMGm7WYe81uZYWLUbkjvz0E\",\n" +
                "    \"cardDescription\": \"Visa •••• 9391\",\n" +
                "    \"cardClass\": \"CREDIT\"\n" +
                "  },\n" +
                "  \"paymentMethodToken\": {\n" +
                "    \"tokenizationType\": \"PAYMENT_GATEWAY\",\n" +
                "    \"token\": \"{\\\"signature\\\":\\\"MEUCIQCBj5ClFzpJTg3UB4yKdXRP5O12vqpcTCM+x51/99ugYAIgPnCmr8k2G4oKWQqxmAawPgTd42vX1rx91eyqIHgSju4\\\\u003d\\\",\\\"protocolVersion\\\":\\\"ECv1\\\",\\\"signedMessage\\\":\\\"{\\\\\\\"encryptedMessage\\\\\\\":\\\\\\\"tSj1k3qfKoVg5rcZg8uOzMaGrk31qTIUkjYehgjV7BVAC/SGVfPE40q9PHppoZJqFsRwM1xMKKpoZatHa/fct/2YlVwxySxNR5uu3PHR4ZzuWb7vh/Nnb8BWlSiUMMELLaHFA1+FR2zbLpgEG9QdmeQRpyq07SETaH43WF0MLTDbKr9Con3SLZVCnoNS6CZZsPVTYNm0IjLWABAf66O5VlMWAPwke+kMaiJmq5ttPaxn6zGVJq4/JT7T7iKuc/T0rWDh7BzzVEzi/wyvaYDl2SkOljqFcjNYMTEr76GHlsgpAjtzCCPJkPJ9m7wkd3v+mgdQgazJ+ZwFvKTk8Ru7wZ4eywgb1G36/+IBpH/7936PvkayOTuXgtQkjts027mnH9/SyRA3unoJrNXRpUPytxwVO048edVUBKB9Q4ZZYadeGErH4xRTg64fPS9KiHSswBeY\\\\\\\",\\\\\\\"ephemeralPublicKey\\\\\\\":\\\\\\\"BLeiRaJ1+9DkK9x8rPK1377KKgQVv3kSbomJZxoTgqaOugBa8PAR3QDIH3VfqscW8zfMbrNrA736Nm4AahbogaU\\\\\\\\u003d\\\\\\\",\\\\\\\"tag\\\\\\\":\\\\\\\"oBPVR6dw8t4GM0ZC3QLd45hcx6oeag5D98BSdIsV0No\\\\\\\\u003d\\\\\\\"}\\\"}\"\n" +
                "  },\n" +
                "  \"email\": \"keinasylum@gmail.com\"\n" +
                "}";

        PaymentData paymentData = new ObjectMapper().readerFor(PaymentData.class).readValue(encCard);
        System.out.println(paymentData);
    }

    @org.junit.Test
    public void testDeserializationDecryptedMsg() throws IOException {
        String decCard = "{\"gatewayMerchantId\":\"fadsfasdf\",\"messageExpiration\":\"1527861216388\",\"messageId\":\"AH2EjteSBP3heqxYKyKkh88NzU95u3sIc6Kup-vwgbF9k3_SzwWBylswvYvHSEdjwzYorh-tQ1VGEvTGht4c2CJdb0Pm9eDzfKtiIjstdl0QcbUWcNQM83ujxFhBmKcHG809dgnsWuOB\",\"paymentMethod\":\"CARD\",\"paymentMethodDetails\":{\"expirationYear\":2023,\"expirationMonth\":12,\"pan\":\"4111111111111111\"}}";
        DecryptedMessage decCardMsg = new ObjectMapper().readerFor(DecryptedMessage.class).readValue(decCard);
        System.out.println(decCardMsg);
        String decToken = "{\n" +
                "\"gatewayMerchantId\":\"fadsfasdf\","+
                "  \"paymentMethod\": \"TOKENIZED_CARD\",\n" +
                "  \"paymentMethodDetails\": {\n" +
                "      \"dpan\": \"4444444444444444\",\n" +
                "      \"expirationMonth\": 10,\n" +
                "      \"expirationYear\": 2020,\n" +
                "      \"authMethod\": \"3DS\",\n" +
                "      \"3dsCryptogram\": \"AAAAAA...\",\n" +
                "      \"3dsEciIndicator\": \"eci indicator\"\n" +
                "  },\n" +
                "  \n" +
                "  \"messageId\": \"some-message-id\",\n" +
                "  \"messageExpiration\": \"1520836260646\"\n" +
                "}";
        DecryptedMessage decTokenMsg = new ObjectMapper().readerFor(DecryptedMessage.class).readValue(decToken);
        System.out.println(decTokenMsg);
    }
}
