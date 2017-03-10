package com.dheeraj.kafka.producer.serializers;

import com.dheeraj.kafka.producer.domain.Mobile;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class MobileDeserializer implements Deserializer<Mobile> {

    private final Schema<Mobile> mobileSchema = RuntimeSchema.getSchema(Mobile.class);

    public Mobile deserialize(String topic, byte[] data) {
        Mobile mobile = mobileSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, mobile, mobileSchema);
        return mobile;
    }

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public void close() {

    }

}
