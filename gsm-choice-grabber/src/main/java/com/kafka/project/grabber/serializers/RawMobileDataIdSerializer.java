package com.kafka.project.grabber.serializers;

import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Serializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RawMobileDataIdSerializer implements Serializer<String> {

    private static final int bufferSize = 2048;
    public static List<Long> serializationTime = new ArrayList<>();
    private static final Schema<String> stringSchema = RuntimeSchema.getSchema(String.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, String data) {
        long startTime = System.currentTimeMillis();
        try {
            byte[] bytes = GraphIOUtil.toByteArray(data, stringSchema, getApplicationBuffer());
            long endTime = System.currentTimeMillis();
            serializationTime.add(endTime - startTime);
            return bytes;
        } finally {
            getApplicationBuffer().clear();
        }
    }

    private static LinkedBuffer getApplicationBuffer() {
        return localBuffer.get();
    }

    private static final ThreadLocal<LinkedBuffer> localBuffer = new ThreadLocal<LinkedBuffer>() {
        public LinkedBuffer initialValue() {
            return LinkedBuffer.allocate(bufferSize);
        }
    };

    @Override
    public void close() {

    }
}
