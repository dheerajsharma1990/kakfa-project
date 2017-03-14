package com.dheeraj.kafka.producer.serializers.deserializers;

import com.dheeraj.kafka.producer.domain.SellerProduct;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class SellerProductDeserializer implements Deserializer<SellerProduct> {

    private final Schema<SellerProduct> sellerProductSchema = RuntimeSchema.getSchema(SellerProduct.class);

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public SellerProduct deserialize(String topic, byte[] data) {
        SellerProduct sellerProduct = sellerProductSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, sellerProduct, sellerProductSchema);
        return sellerProduct;
    }

    public void close() {

    }

}
