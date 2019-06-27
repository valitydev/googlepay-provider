package com.rbkmoney.provider.googlepay.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rbkmoney.provider.googlepay.domain.Card;
import com.rbkmoney.provider.googlepay.domain.DecryptedMessage;
import com.rbkmoney.provider.googlepay.domain.TokenizedCard;

import java.io.IOException;

public class DecryptedMessageDeserializer extends StdDeserializer<DecryptedMessage> {
    private final ObjectMapper mapper;


    public DecryptedMessageDeserializer() {
        super(DecryptedMessage.class);
        JacksonAnnotationIntrospector introspector = new JacksonAnnotationIntrospector() {
            @Override
            public Object findDeserializer(Annotated a) {
                if (a.getRawType() == DecryptedMessage.class)
                    return null;
                return super.findDeserializer(a);
            }
        };
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        mapper.setAnnotationIntrospector(introspector);
    }

    @Override
    public DecryptedMessage deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode root = parser.readValueAsTree();

        DecryptedMessage message = mapper.treeToValue(root, DecryptedMessage.class);

        switch (message.getPaymentMethod()) {
            case CARD:
                message.setPaymentCredential(mapper.convertValue(message.getPaymentCredentialMap(), Card.class));
                break;
            case TOKENIZED:
                message.setPaymentCredential(mapper.convertValue(message.getPaymentCredentialMap(), TokenizedCard.class));
                break;
        }

        return message;
    }
}
