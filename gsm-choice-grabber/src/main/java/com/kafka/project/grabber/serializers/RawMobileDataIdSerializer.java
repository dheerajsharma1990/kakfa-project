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
    private static final Schema schema = RuntimeSchema.getSchema(String.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, String data) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(ProtostuffIOUtil.toByteArray(data, schema, getApplicationBuffer()));
            byte[] bytes = byteArrayOutputStream.toByteArray();
            gzipOutputStream.close();
            byteArrayOutputStream.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
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
