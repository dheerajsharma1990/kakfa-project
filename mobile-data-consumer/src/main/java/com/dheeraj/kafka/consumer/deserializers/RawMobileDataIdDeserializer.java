package com.dheeraj.kafka.consumer.deserializers;

import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class RawMobileDataIdDeserializer implements Deserializer<String> {

    private final Schema<String> mobileIdSchema = RuntimeSchema.getSchema(String.class);

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public String deserialize(String topic, byte[] data) {
        //String mobileId = mobileIdSchema.newMessage();
        //ProtostuffIOUtil.mergeFrom(decompress(data), mobileId, mobileIdSchema);
        return "";
    }

    private byte[] decompress(byte[] data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            IOUtils.copy(new GZIPInputStream(new ByteArrayInputStream(data)), out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out.toByteArray();
    }

    public void close() {

    }

}
