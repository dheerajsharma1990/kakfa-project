package com.dheeraj.kafka.producer.serializers;

import com.dheeraj.kafka.producer.domain.Domain;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class DomainSerializer implements Serializer<Domain> {

    private static final int bufferSize = 2048;

    @Override
    public byte[] serialize(String topic, Domain data) {
        try {
            Schema schema = RuntimeSchema.getSchema(data.getDomainObject().getClass());
            return ProtostuffIOUtil.toByteArray(data, schema,
                    getApplicationBuffer());
        } finally {
            getApplicationBuffer().clear();
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public void close() {

    }

    private static LinkedBuffer getApplicationBuffer() {
        return localBuffer.get();
    }

    private static final ThreadLocal<LinkedBuffer> localBuffer = new ThreadLocal<LinkedBuffer>() {
        public LinkedBuffer initialValue() {
            return LinkedBuffer.allocate(bufferSize);
        }
    };
}
