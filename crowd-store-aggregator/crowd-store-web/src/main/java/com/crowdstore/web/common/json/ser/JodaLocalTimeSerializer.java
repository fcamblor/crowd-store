package com.crowdstore.web.common.json.ser;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.codehaus.jackson.map.ser.std.StdKeySerializers;
import org.joda.time.LocalTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author fcamblor
 */
public class JodaLocalTimeSerializer extends SerializerBase<LocalTime> {

    private StdKeySerializers.StringKeySerializer delegate = new StdKeySerializers.StringKeySerializer();

    public JodaLocalTimeSerializer() {
        super(LocalTime.class);
    }

    @Override
    public void serialize(LocalTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        String strVal = null;
        if (value != null) {
            strVal = ISODateTimeFormat.localTimeParser().print(value);
        }
        delegate.serialize(strVal, jgen, provider);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return delegate.getSchema(provider, typeHint);
    }
}