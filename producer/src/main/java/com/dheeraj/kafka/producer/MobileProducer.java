package com.dheeraj.kafka.producer;

import com.dheeraj.kafka.producer.domain.Mobile;
import com.dheeraj.kafka.producer.domain.MobileId;
import org.apache.kafka.clients.producer.*;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;

public class MobileProducer {

    private static final String topicName = "MOBILES";

    public static void main(String[] argv) throws Exception {
        MobileId mobileId = new MobileId("MOB001");
        Mobile mobile = new Mobile(mobileId, "One Plus 3");

        Properties myProductTopicConfigProperties = getMyProductTopicConfigProperties();
        Producer<MobileId, Mobile> productProducer = new KafkaProducer<>(myProductTopicConfigProperties);

        ProducerRecord<MobileId, Mobile> record = new ProducerRecord<>(topicName, mobileId, mobile);

        Future<RecordMetadata> recordMetadataFuture = productProducer.send(record);
        RecordMetadata recordMetadata = recordMetadataFuture.get();
        System.out.println("Timestamp of record in Kafka " + new Date(recordMetadata.timestamp()));
        productProducer.close();
    }

    private static Properties getMyProductTopicConfigProperties() {
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.serializers.DomainIdSerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.dheeraj.kafka.producer.serializers.DomainSerializer");
        return configProperties;
    }


}
