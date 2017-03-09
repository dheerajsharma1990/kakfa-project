package com.dheeraj.kafka.producer;

import com.dheeraj.kafka.producer.domain.Product;
import com.dheeraj.kafka.producer.domain.ProductId;
import io.protostuff.LinkedBuffer;
import org.apache.kafka.clients.producer.*;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;

public class ProductProducer {

    private static final String topicName = "MY_PRODUCT";

    private static final int bufferSize = 2048;

    public static void main(String[] argv) throws Exception {
        ProductId productId = new ProductId("CUP001");
        Product product = new Product(productId, "Ceramic Cup");

        Properties myProductTopicConfigProperties = getMyProductTopicConfigProperties();
        Producer<ProductId, Product> productProducer = new KafkaProducer<ProductId, Product>(myProductTopicConfigProperties);

        ProducerRecord<ProductId, Product> record = new ProducerRecord<ProductId, Product>(topicName, productId, product);

        Future<RecordMetadata> recordMetadataFuture = productProducer.send(record);
        RecordMetadata recordMetadata = recordMetadataFuture.get();
        System.out.println("Timestamp of record in Kafka " + new Date(recordMetadata.timestamp()));
        productProducer.close();
        getApplicationBuffer().clear();
    }

    private static LinkedBuffer getApplicationBuffer() {
        return localBuffer.get();
    }

    private static final ThreadLocal<LinkedBuffer> localBuffer = new ThreadLocal<LinkedBuffer>() {
        public LinkedBuffer initialValue() {
            return LinkedBuffer.allocate(bufferSize);
        }
    };

    private static Properties getMyProductTopicConfigProperties() {
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.serializers.ProductIdSerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.serializers.ProductSerializer");
        return configProperties;
    }


}
