package com.kafka.project.grabber.serializers;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPOutputStream;

public class RawMobileDataIdSerializer implements Serializer<String> {

    private static final int bufferSize = 2048;
    private static final Schema<String> mobileIdSchema = RuntimeSchema.getSchema(String.class);
    public static AtomicLong serializationTime = new AtomicLong(0l);
    public static AtomicLong compressionTime = new AtomicLong(0l);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, String data) {
        try {
            long startTime = System.currentTimeMillis();
            byte[] serialized = ProtostuffIOUtil.toByteArray(data, mobileIdSchema, getApplicationBuffer());
            long endTime = System.currentTimeMillis();
            serializationTime.addAndGet(endTime - startTime);
            //byte[] compressed = compress(serialized);
            startTime = System.currentTimeMillis();
            //compressionTime.addAndGet(startTime - endTime);
            return serialized;
        } /*catch (IOException e) {
            throw new RuntimeException(e);
        } */finally {
            getApplicationBuffer().clear();
        }
    }

    private byte[] compress(byte[] content) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            try {
                gzipOutputStream.write(content);
            } finally {
                gzipOutputStream.close();
            }
        } finally {
            byteArrayOutputStream.close();
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
