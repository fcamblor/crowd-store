package com.crowdstore.web.common.json.deser;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import org.codehaus.jackson.map.deser.std.StringDeserializer;
import org.joda.time.LocalTime;

import java.io.IOException;

/**
 * @author fcamblor
 */
public class JodaLocalTimeDeserializer extends StdScalarDeserializer<LocalTime> {

    private StringDeserializer delegate = new StringDeserializer();

    public JodaLocalTimeDeserializer() {
        super(LocalTime.class);
    }

    @Override
    public LocalTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String text = delegate.deserialize(jp, ctxt);
        if (text == null) {
            return null;
        }

        try {
            return LocalTime.parse(text);
        } catch (IllegalArgumentException iae) {
            throw ctxt.weirdStringException(_valueClass, "not a valid representation");
        }
    }
}
