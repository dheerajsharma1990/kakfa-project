package com.dheeraj.kafka.producer.serializers;

import com.dheeraj.kafka.producer.domain.Mobile;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class MobileSerializer implements Serializer<Mobile> {

    private static final int bufferSize = 2048;

    private final Schema productSchema = RuntimeSchema.getSchema(Mobile.class);

    public byte[] serialize(String topic, Mobile mobile) {
        try {
            return ProtostuffIOUtil.toByteArray(mobile, productSchema,
                    getApplicationBuffer());
        } finally {
            getApplicationBuffer().clear();
        }
    }

    public void configure(Map<String, ?> configs, boolean isKey) {
    }

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
