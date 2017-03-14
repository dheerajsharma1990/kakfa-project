package com.dheeraj.kafka.producer;

import com.dheeraj.kafka.producer.domain.*;
import org.apache.kafka.clients.producer.*;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;

public class MobileProducer {

    private static final String sellerProductTopic = "SELLER_PRODUCT";

    public static void main(String[] argv) throws Exception {
        Properties myProductTopicConfigProperties = getMyProductTopicConfigProperties();
        Producer producer = new KafkaProducer<>(myProductTopicConfigProperties);
        try {
            MobileId mobileId = new MobileId("MOB001");
            SellerId sellerId = new SellerId("SELLER1");
            SellerProductId sellerProductId = new SellerProductId(sellerId, mobileId);
            SellerProduct sellerProduct = new SellerProduct(sellerProductId, 499);
            Future<RecordMetadata> recordMetadataFuture = sendSellerProduct(sellerProductId, sellerProduct, producer);
            RecordMetadata recordMetadata = recordMetadataFuture.get();
            System.out.println("Timestamp of record in Kafka " + new Date(recordMetadata.timestamp()));
        } finally {
            producer.close();
        }
    }

    private static Future<RecordMetadata> sendSellerProduct(SellerProductId sellerProductId, SellerProduct sellerProduct, Producer producer) {
        ProducerRecord<SellerProductId, SellerProduct> sellerProductProducerRecord = new ProducerRecord<>(sellerProductTopic, sellerProductId, sellerProduct);
        return producer.send(sellerProductProducerRecord);
    }

    private static Properties getMyProductTopicConfigProperties() {
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.serializers.DomainIdSerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.serializers.DomainSerializer");
        return configProperties;
    }


}
