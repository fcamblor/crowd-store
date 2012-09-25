package com.crowdstore.web.common.json.deser;

import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdKeyDeserializer;
import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

/**
 * @author fcamblor
 */
public class JodaLocalDateKeyDeserializer extends StdKeyDeserializer {

    public JodaLocalDateKeyDeserializer() {
        super(LocalDate.class);
    }

    @Override
    protected Object _parse(String key, DeserializationContext ctxt) throws Exception {
        return ISODateTimeFormat.localDateParser().parseLocalDate(key);
    }
}

