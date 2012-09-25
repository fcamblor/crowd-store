package com.crowdstore.web.common.json.ser;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.StdSerializers;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author fcamblor
 */
public class JodaLocalDateSerializer extends SerializerBase<LocalDate> {

    private StdSerializers.UtilDateSerializer delegate = new StdSerializers.UtilDateSerializer();

    public JodaLocalDateSerializer() {
        super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        Date date = null;
        if (value != null) {
            date = value.toDate();
        }
        delegate.serialize(date, jgen, provider);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return delegate.getSchema(provider, typeHint);
    }
}