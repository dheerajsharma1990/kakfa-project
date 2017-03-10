package com.dheeraj.kafka.producer.serializers;

import com.dheeraj.kafka.producer.domain.MobileId;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class MobileIdDeserializer implements Deserializer<MobileId> {

    private final Schema<MobileId> productIdSchema = RuntimeSchema.getSchema(MobileId.class);

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public MobileId deserialize(String topic, byte[] data) {
        MobileId mobileId = productIdSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, mobileId, productIdSchema);
        return mobileId;
    }

    public void close() {

    }

}
