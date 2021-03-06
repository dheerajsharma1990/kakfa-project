package com.dheeraj.kafka.consumer;

import com.kafka.project.gsm.domain.RawMobileData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class RawMobileDataConsumer {

    public static void main(String[] argv) throws Exception {
        String topicName = "MOBILES";
        String groupId = "mobileConsumerGroup";

        Properties productConsumerProperties = new Properties();
        productConsumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.132.0.2:9092,10.132.0.3:9092,10.132.0.4:9092");
        productConsumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.consumer.deserializers.RawMobileDataIdDeserializer");
        productConsumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.consumer.deserializers.RawMobileDataDeserializer");
        productConsumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        productConsumerProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");


        KafkaConsumer<String, RawMobileData> productKafkaConsumer = new KafkaConsumer<>(productConsumerProperties);
        List<RawMobileData> rawMobileDatas = new ArrayList<>();
        try {
            productKafkaConsumer.subscribe(Arrays.asList(topicName));
            long startTime = System.currentTimeMillis();
            while (rawMobileDatas.size() != 13998) {
                ConsumerRecords<String, RawMobileData> rawMobileDataConsumerRecords = productKafkaConsumer.poll(200);
                if (!rawMobileDataConsumerRecords.isEmpty()) {
                    for (ConsumerRecord<String, RawMobileData> record : rawMobileDataConsumerRecords) {
                        rawMobileDatas.add(record.value());
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken to consume all records [" + rawMobileDatas.size() + "] is " + (endTime - startTime) + " millis.");
        } finally {
            productKafkaConsumer.close();
        }

    }

}