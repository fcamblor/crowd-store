package com.crowdstore.web.common.json.deser;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.util.Date;

/**
 * @author fcamblor
 */
public class JodaLocalDateDeserializer extends StdScalarDeserializer<LocalDate> {

    private SqlDateDeserializer delegate = new SqlDateDeserializer();

    public JodaLocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Date d = delegate.deserialize(jp, ctxt);
        if (d == null) {
            return null;
        }

        try {
            return new LocalDate(d);
        } catch (IllegalArgumentException iae) {
            throw ctxt.weirdStringException(_valueClass, "not a valid representation");
        }
    }
}
