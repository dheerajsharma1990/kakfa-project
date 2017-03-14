package com.dheeraj.kafka.producer.serializers.deserializers;

import com.dheeraj.kafka.producer.domain.SellerProductId;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class SellerProductIdDeserializer implements Deserializer<SellerProductId> {

    private final Schema<SellerProductId> sellerProductIdSchema = RuntimeSchema.getSchema(SellerProductId.class);

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public SellerProductId deserialize(String topic, byte[] data) {
        SellerProductId sellerProductId = sellerProductIdSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, sellerProductId, sellerProductIdSchema);
        return sellerProductId;
    }

    public void close() {

    }

}
