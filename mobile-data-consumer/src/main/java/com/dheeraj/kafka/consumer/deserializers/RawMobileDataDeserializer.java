package com.dheeraj.kafka.consumer.deserializers;

import com.kafka.project.gsm.domain.RawMobileData;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class RawMobileDataDeserializer implements Deserializer<RawMobileData> {

    private final Schema<RawMobileData> rawMobileDataSchema = RuntimeSchema.getSchema(RawMobileData.class);

    public RawMobileData deserialize(String topic, byte[] data) {
        RawMobileData rawMobileData = rawMobileDataSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(decompress(data), rawMobileData, rawMobileDataSchema);
        return rawMobileData;
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


    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public void close() {

    }

}
