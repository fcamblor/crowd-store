package com.crowdstore.web.common.json.ser;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.codehaus.jackson.map.ser.std.StdKeySerializers;
import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * @author fcamblor
 */
public class JodaLocalDateKeySerializer extends SerializerBase<LocalDate> {
    private StdKeySerializers.StringKeySerializer delegate = new StdKeySerializers.StringKeySerializer();

    public JodaLocalDateKeySerializer() {
        super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        String strVal = null;
        if (value != null) {
            strVal = ISODateTimeFormat.localDateParser().print(value);
        }
        delegate.serialize(strVal, jgen, provider);
    }
}
