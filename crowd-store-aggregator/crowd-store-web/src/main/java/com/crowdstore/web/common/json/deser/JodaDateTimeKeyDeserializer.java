package com.crowdstore.web.common.json.deser;

import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdKeyDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * @author fcamblor
 */
public class JodaDateTimeKeyDeserializer extends StdKeyDeserializer {

    public JodaDateTimeKeyDeserializer() {
        super(DateTime.class);
    }

    @Override
    protected Object _parse(String key, DeserializationContext ctxt) throws Exception {
        return ISODateTimeFormat.dateTimeParser().parseDateTime(key);
    }
}