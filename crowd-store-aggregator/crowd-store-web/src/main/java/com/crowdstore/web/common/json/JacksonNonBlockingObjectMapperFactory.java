package com.crowdstore.web.common.json;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.codehaus.jackson.map.deser.std.StdKeyDeserializer;
import org.codehaus.jackson.map.module.SimpleModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fcamblor
 *         Custom Jackson Object mapper factory which won't block when a type parsing error will occur during
 *         deserialization
 *         Target field will simply valued to null, nothing more
 */
public class JacksonNonBlockingObjectMapperFactory {
    /**
     * Deserializer that won't block if value parsing doesn't match with target type
     *
     * @param <T> Handled type
     */
    private static class NonBlockingDeserializer<T> extends JsonDeserializer<T> {
        private StdDeserializer<T> delegate;

        public NonBlockingDeserializer(StdDeserializer<T> _delegate) {
            this.delegate = _delegate;
        }

        @Override
        public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            try {
                return delegate.deserialize(jp, ctxt);
            } catch (JsonMappingException e) {
                // If a JSON Mapping occurs, simply returning null instead of blocking things
                return null;
            }
        }
    }

    private static class NonBlockingKeyDeserializer extends KeyDeserializer {
        private StdKeyDeserializer delegate;

        public NonBlockingKeyDeserializer(StdKeyDeserializer delegate) {
            this.delegate = delegate;
        }

        @Override
        public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            try {
                return delegate.deserializeKey(key, ctxt);
            } catch (JsonMappingException e) {
                // If a JSON Mapping occurs, simply returning null instead of blocking things
                return null;
            }
        }
    }

    private List<JsonSerializer> jsonSerializers = new ArrayList<JsonSerializer>();
    private List<StdDeserializer> jsonDeserializers = new ArrayList<StdDeserializer>();
    private List<StdKeyDeserializer> jsonKeyDeserializers = new ArrayList<StdKeyDeserializer>();
    private List<JsonSerializer> jsonKeySerializers = new ArrayList<JsonSerializer>();

    public ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule appJacksonModule = new SimpleModule("appJacksonModule", new Version(1, 0, 0, null));
        for (JsonSerializer jsonSerializer : jsonSerializers) {
            // Not wrapping anything here... there isn't any blocking thing during serialization
            appJacksonModule.addSerializer(jsonSerializer.handledType(), jsonSerializer);
        }
        for (JsonSerializer jsonKeySerializer : jsonKeySerializers) {
            // Not wrapping anything here... there isn't any blocking thing during serialization
            appJacksonModule.addKeySerializer(jsonKeySerializer.handledType(), jsonKeySerializer);
        }
        for (StdDeserializer jsonDeserializer : jsonDeserializers) {
            // Wrapping given deserializers with NonBlockingDeserializer
            appJacksonModule.addDeserializer(jsonDeserializer.getValueClass(), new NonBlockingDeserializer(jsonDeserializer));
        }
        for (StdKeyDeserializer jsonKeyDeserializer : jsonKeyDeserializers) {
            // Wrapping given keydeserializers with NonBlockingDeserializer
            appJacksonModule.addKeyDeserializer(jsonKeyDeserializer.getKeyClass(), new NonBlockingKeyDeserializer(jsonKeyDeserializer));
        }

        objectMapper.registerModule(appJacksonModule);
        return objectMapper;
    }

    public JacksonNonBlockingObjectMapperFactory setJsonDeserializers(List<StdDeserializer> _jsonDeserializers) {
        this.jsonDeserializers = _jsonDeserializers;
        return this;
    }

    public JacksonNonBlockingObjectMapperFactory setJsonSerializers(List<JsonSerializer> _jsonSerializers) {
        this.jsonSerializers = _jsonSerializers;
        return this;
    }

    public JacksonNonBlockingObjectMapperFactory setJsonKeyDeserializers(List<StdKeyDeserializer> _jsonKeyDeserializers) {
        this.jsonKeyDeserializers = _jsonKeyDeserializers;
        return this;
    }

    public JacksonNonBlockingObjectMapperFactory setJsonKeySerializers(List<JsonSerializer> _jsonKeySerializers) {
        this.jsonKeySerializers = _jsonKeySerializers;
        return this;
    }
}
