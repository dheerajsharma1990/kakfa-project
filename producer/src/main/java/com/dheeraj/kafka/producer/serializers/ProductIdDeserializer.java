package com.dheeraj.kafka.producer.serializers;

import com.dheeraj.kafka.producer.domain.ProductId;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ProductIdDeserializer implements Deserializer<ProductId> {

    private final Schema<ProductId> productIdSchema = RuntimeSchema.getSchema(ProductId.class);

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public ProductId deserialize(String topic, byte[] data) {
        ProductId productId = productIdSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, productId, productIdSchema);
        return productId;
    }

    public void close() {

    }

}
