package com.dheeraj.kafka.consumer;

import com.kafka.project.gsm.domain.RawMobileData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

public class RawMobileDataConsumer {

    public static void main(String[] argv) throws Exception {
        String topicName = "MOBILES";
        String groupId = "mobileConsumerGroup";

        Properties productConsumerProperties = new Properties();
        productConsumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        productConsumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.deserializers.RawMobileDataIdDeserializer");
        productConsumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.deserializers.RawMobileDataDeserializer");
        productConsumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        productConsumerProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");;

        KafkaConsumer<String, RawMobileData> productKafkaConsumer = new KafkaConsumer<>(productConsumerProperties);
        List<RawMobileData> rawMobileDatas = new ArrayList<>();
        try {
            productKafkaConsumer.assign(Arrays.asList(new TopicPartition(topicName, 0)));
            productKafkaConsumer.seekToBeginning(Arrays.asList(new TopicPartition(topicName, 0)));
            while (true) {
                ConsumerRecords<String, RawMobileData> rawMobileDataConsumerRecords = productKafkaConsumer.poll(1000);
                if (!rawMobileDataConsumerRecords.isEmpty()) {
                    System.out.println("Started consuming raw mobile data at " + new Date());
                    for (ConsumerRecord<String, RawMobileData> record : rawMobileDataConsumerRecords) {
                        rawMobileDatas.add(record.value());
                    }
                    System.out.println("Consumed raw mobiles data at " + new Date());
                    System.out.println("Total Records " + rawMobileDatas.size());
                }
            }
        } finally {
            productKafkaConsumer.close();
        }

    }

}