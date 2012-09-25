package com.crowdstore.web.common.json.deser;

import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdKeyDeserializer;
import org.joda.time.LocalTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * @author fcamblor
 */
public class JodaLocalTimeKeyDeserializer extends StdKeyDeserializer {
    public JodaLocalTimeKeyDeserializer() {
        super(LocalTime.class);
    }

    @Override
    protected Object _parse(String key, DeserializationContext ctxt) throws Exception {
        return ISODateTimeFormat.localTimeParser().parseLocalTime(key);
    }
}
