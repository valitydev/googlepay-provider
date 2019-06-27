package com.rbkmoney.provider.googlepay;

import com.rbkmoney.damsel.base.Content;
import com.rbkmoney.damsel.base.InvalidRequest;
import com.rbkmoney.damsel.payment_tool_provider.*;
import com.rbkmoney.woody.thrift.impl.http.THClientBuilder;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class IntegrationTest {
    @Value("http://127.0.0.1:${server.port}/provider/google")
    private String url;

    private PaymentToolProviderSrv.Iface client;

    private String encMsg = "{\n" +
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

    private String encMechId = "rbkmoney";

    @Before
    public void setUp() throws URISyntaxException {
        client = new THClientBuilder().withAddress(new URI(url)).build(PaymentToolProviderSrv.Iface.class);
    }

    @Test
    public void testCard() throws TException {
        UnwrappedPaymentTool res = null;
        try {
            res = client.unwrap(new WrappedPaymentTool(PaymentRequest.google(new GooglePayRequest("test" + encMechId, toContent(encMsg)))));
        } catch (InvalidRequest e) {
            if (!e.getErrors().get(0).contains("expired payload"))
                fail("Token must be expired");

        }
        try {
            res = client.unwrap(new WrappedPaymentTool(PaymentRequest.google(new GooglePayRequest(encMechId, toContent(encMsg)))));
        } catch (InvalidRequest e) {
            if (!e.getErrors().get(0).contains("cannot verify signature"))
                fail("Merchant id must be identified as prod, sign verification fail expected");
        }
          /*      assertEquals("4111111111111111", res.getPaymentData().getCard().getPan());
                assertEquals(new ExpDate((byte) 12, (short) 2023), res.getPaymentData().getCard().getExpDate());

                assertEquals(CardClass.credit, res.getCardInfo().getCardClass());
                assertEquals(BankCardPaymentSystem.visa, res.getCardInfo().getPaymentSystem());
                assertEquals("Visa •••• 9391", res.getCardInfo().getDisplayName());
                assertEquals("9391", res.getCardInfo().getLast4Digits());

                assertEquals("2018-06-05T12:36:44.549Z", res.getDetails().getGoogle().getMessageExpiration());
                assertEquals("AH2EjtfIuPojiNSCv4SarecQOmML6hX1x-0yOZImfiPubV_vd6vVc0vScpQV0ExK1eJ2C9_D1KSeg4T45QyurCF6wTCJMoIgJR4zYcak0cAZ6XXOUk_hs72b45NLHbHEvAziG7ZJmLhv", res.getDetails().getGoogle().getMessageId());
        */
    }

    @Test(expected = InvalidRequest.class)
    public void testWrongMerchantId() throws TException {
        client.unwrap(new WrappedPaymentTool(PaymentRequest.google(new GooglePayRequest(encMechId + "1", toContent(encMsg)))));
    }

    private Content toContent(String data) {
        return new Content("application/json", ByteBuffer.wrap(data.getBytes()));
    }


}
