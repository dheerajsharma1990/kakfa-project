package com.dheeraj.kafka.producer;

import com.dheeraj.kafka.producer.domain.Mobile;
import com.dheeraj.kafka.producer.domain.MobileId;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class MobileConsumer {

    public static void main(String[] argv) throws Exception {
        String topicName = "MOBILES";
        String groupId = "mygroup";

        Properties productConsumerProperties = new Properties();
        productConsumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        productConsumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.serializers.MobileIdDeserializer");
        productConsumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.serializers.MobileDeserializer");
        productConsumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        productConsumerProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");

        KafkaConsumer<MobileId, Mobile> productKafkaConsumer = new KafkaConsumer<>(productConsumerProperties);
        productKafkaConsumer.subscribe(Arrays.asList(topicName));
        ConsumerRecords<MobileId, Mobile> productRecords = productKafkaConsumer.poll(1000);
        for (ConsumerRecord<MobileId, Mobile> consumerRecord : productRecords) {
            System.out.println("Value: " + consumerRecord.value() + " Offset: " + consumerRecord.offset());
        }
        productKafkaConsumer.close();
    }

}