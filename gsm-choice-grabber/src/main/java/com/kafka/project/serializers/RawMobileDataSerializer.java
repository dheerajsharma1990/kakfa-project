package com.kafka.project.serializers;

import com.kafka.project.gsm.domain.RawMobileData;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class RawMobileDataSerializer implements Serializer<RawMobileData> {

    private static final int bufferSize = 2048;
    private static final Schema schema = RuntimeSchema.getSchema(RawMobileData.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, RawMobileData data) {
        try {
            return ProtostuffIOUtil.toByteArray(data, schema,
                    getApplicationBuffer());
        } finally {
            getApplicationBuffer().clear();
        }
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