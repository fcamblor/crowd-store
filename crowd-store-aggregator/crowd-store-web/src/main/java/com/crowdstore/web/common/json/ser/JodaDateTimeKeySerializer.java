package com.crowdstore.web.common.json.ser;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.codehaus.jackson.map.ser.std.StdKeySerializers;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * @author fcamblor
 */
public class JodaDateTimeKeySerializer extends SerializerBase<DateTime> {
    private StdKeySerializers.StringKeySerializer delegate = new StdKeySerializers.StringKeySerializer();

    public JodaDateTimeKeySerializer() {
        super(DateTime.class);
    }

    @Override
    public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        String strVal = null;
        if (value != null) {
            strVal = ISODateTimeFormat.dateTime().print(value);
        }
        delegate.serialize(strVal, jgen, provider);
    }
}
