package com.dheeraj.kafka.producer.serializers;

import com.dheeraj.kafka.producer.domain.Product;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ProductDeserializer implements Deserializer<Product> {

    private final Schema<Product> productSchema = RuntimeSchema.getSchema(Product.class);

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public Product deserialize(String topic, byte[] data) {
        Product product = productSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, product, productSchema);
        return product;
    }

    public void close() {

    }

}
