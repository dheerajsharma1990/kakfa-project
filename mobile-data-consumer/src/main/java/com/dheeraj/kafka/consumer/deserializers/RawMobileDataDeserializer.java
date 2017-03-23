package com.dheeraj.kafka.consumer.deserializers;

import com.kafka.project.gsm.domain.RawMobileData;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class RawMobileDataDeserializer implements Deserializer<RawMobileData> {

    private final Schema<RawMobileData> rawMobileDataSchema = RuntimeSchema.getSchema(RawMobileData.class);

    public RawMobileData deserialize(String topic, byte[] data) {
        RawMobileData rawMobileData = rawMobileDataSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, rawMobileData, rawMobileDataSchema);
        return rawMobileData;
    }

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public void close() {

    }

}
