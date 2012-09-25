package com.crowdstore.web.common.json.deser;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.CalendarDeserializer;
import org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Calendar;

/**
 * @author fcamblor
 */
public class JodaDateTimeDeserializer extends StdScalarDeserializer<DateTime> {

    private CalendarDeserializer delegate = new CalendarDeserializer();

    public JodaDateTimeDeserializer() {
        super(DateTime.class);
    }

    @Override
    public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Calendar c = delegate.deserialize(jp, ctxt);
        if (c == null) {
            return null;
        }

        try {
            return new DateTime(c);
        } catch (IllegalArgumentException iae) {
            throw ctxt.weirdStringException(_valueClass, "not a valid representation");
        }
    }
}