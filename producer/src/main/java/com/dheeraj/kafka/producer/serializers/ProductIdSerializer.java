package com.dheeraj.kafka.producer.serializers;

import com.dheeraj.kafka.producer.domain.ProductId;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ProductIdSerializer implements Serializer<ProductId> {

    private static final int bufferSize = 2048;

    private final Schema productIdSchema = RuntimeSchema.getSchema(ProductId.class);

    public byte[] serialize(String topic, ProductId productId) {
        try {
            return ProtostuffIOUtil.toByteArray(productId, productIdSchema,
                    getApplicationBuffer());
        } finally {
            getApplicationBuffer().clear();
        }
    }

    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    public void close() {
    }

    private static LinkedBuffer getApplicationBuffer() {
        return localBuffer.get();
    }

    private static final ThreadLocal<LinkedBuffer> localBuffer = new ThreadLocal<LinkedBuffer>() {
        public LinkedBuffer initialValue() {
            return LinkedBuffer.allocate(bufferSize);
        }
    };
}
