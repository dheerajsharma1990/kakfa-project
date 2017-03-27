package com.kafka.project.grabber.serializers;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class RawMobileDataIdSerializer implements Serializer<String> {

    private static final int bufferSize = 2048;
    private static final Schema<String> mobileIdSchema = RuntimeSchema.getSchema(String.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, String data) {
        try {
            return compress(ProtostuffIOUtil.toByteArray(data, mobileIdSchema, getApplicationBuffer()));
        } finally {
            getApplicationBuffer().clear();
        }
    }

    private byte[] compress(byte[] content) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(content);
            gzipOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
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
