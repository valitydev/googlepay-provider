package com.rbkmoney.provider.googlepay.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.rbkmoney.provider.googlepay.domain.Auth3DS;
import com.rbkmoney.provider.googlepay.domain.TokenizedCard;

import java.io.IOException;

public class TokenizedCardDeserializer extends JsonDeserializer<TokenizedCard> {
    private final ObjectMapper mapper;

    public TokenizedCardDeserializer() {
        JacksonAnnotationIntrospector introspector = new JacksonAnnotationIntrospector() {
            @Override
            public Object findDeserializer(Annotated a) {
                if (a.getRawType() == TokenizedCard.class)
                    return null;
                return super.findDeserializer(a);
            }
        };
        mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(introspector);
    }

    @Override
    public TokenizedCard deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode root = parser.readValueAsTree();
        TokenizedCard tokenizedCard = mapper.treeToValue(root, TokenizedCard.class);

        switch (tokenizedCard.getAuthType()) {
            case AUTH_3DS:
                tokenizedCard.setAuth(mapper.reader().treeToValue(root, Auth3DS.class));
                break;
        }

        return tokenizedCard;
    }
}
