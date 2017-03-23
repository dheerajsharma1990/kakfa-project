package com.dheeraj.kafka.consumer.deserializers;

import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class RawMobileDataIdDeserializer implements Deserializer<String> {

    private final Schema<String> mobileIdSchema = RuntimeSchema.getSchema(String.class);

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public String deserialize(String topic, byte[] data) {
        String mobileId = mobileIdSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, mobileId, mobileIdSchema);
        return mobileId;
    }

    public void close() {

    }

}
