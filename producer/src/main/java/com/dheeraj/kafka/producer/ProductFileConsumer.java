package com.dheeraj.kafka.producer;

import com.dheeraj.kafka.producer.domain.Product;
import com.dheeraj.kafka.producer.domain.ProductId;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class ProductFileConsumer {

    public static void main(String[] argv) throws Exception {
        String topicName = "MY_PRODUCT";
        String groupId = "mygroup";

        Properties productConsumerProperties = new Properties();
        productConsumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        productConsumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.serializers.ProductIdDeserializer");
        productConsumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.serializers.ProductDeserializer");
        productConsumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        productConsumerProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");

        KafkaConsumer<ProductId, Product> productKafkaConsumer = new KafkaConsumer<ProductId, Product>(productConsumerProperties);
        productKafkaConsumer.subscribe(Arrays.asList(topicName));
        ConsumerRecords<ProductId, Product> productRecords = productKafkaConsumer.poll(1000);
        for(ConsumerRecord<ProductId,Product> consumerRecord : productRecords) {
            System.out.println(consumerRecord.value());
        }
        productKafkaConsumer.close();
    }

}