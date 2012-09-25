package com.crowdstore.web.common.json.ser;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.StdSerializers;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.joda.time.DateTime;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.GregorianCalendar;

/**
 * @author fcamblor
 */
public class JodaDateTimeSerializer extends SerializerBase<DateTime> {

    private StdSerializers.CalendarSerializer delegate = new StdSerializers.CalendarSerializer();

    public JodaDateTimeSerializer() {
        super(DateTime.class);
    }

    @Override
    public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        GregorianCalendar cal = null;
        if (value != null) {
            cal = value.toGregorianCalendar();
        }
        delegate.serialize(cal, jgen, provider);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return delegate.getSchema(provider, typeHint);
    }
}
