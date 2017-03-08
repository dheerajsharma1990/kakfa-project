package com.dheeraj.kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;

public class ProductFileProducer {

    public static void main(String[] argv) throws Exception {
        String topicName = "javaworld";

        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String,String> producer = new KafkaProducer<String, String>(configProperties);
        ProducerRecord<String, String> rec = new ProducerRecord<String, String>(topicName, "Other Line");
        Future<RecordMetadata> future = producer.send(rec);
        RecordMetadata recordMetadata = future.get();
        System.out.println("Timestamp of record in Kafka " + new Date(recordMetadata.timestamp()));
        producer.close();
    }
}
