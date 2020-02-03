package org.scadalts.e2e.webservice.impl.services.cmp;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.math.BigInteger;

public class CmpParamsDeserializer extends JsonDeserializer<CmpParams> {

    @Override
    public CmpParams deserialize(JsonParser jp, DeserializationContext dc)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String error = node.get("error").asText();
        String resultOperationSave = node.get("resultOperationSave").asText();
        String xid = node.get("xid").asText();
        String value = node.get("value").asText();
        int raw = new BigInteger(value).intValue();

        return CmpParams.builder()
                .error(error)
                .resultOperationSave(resultOperationSave)
                .value(String.valueOf(raw))
                .xid(xid)
                .build();
    }
}