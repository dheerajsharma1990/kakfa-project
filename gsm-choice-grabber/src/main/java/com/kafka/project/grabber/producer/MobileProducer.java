package com.kafka.project.grabber.producer;

import com.kafka.project.grabber.ReadData;
import com.kafka.project.gsm.domain.RawMobileData;
import org.apache.kafka.clients.producer.*;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

public class MobileProducer {

    private static final String mobilesTopic = "MOBILES";

    public static void main(String[] argv) throws Exception {
        Properties myProductTopicConfigProperties = getMyProductTopicConfigProperties();
        Producer producer = new KafkaProducer<>(myProductTopicConfigProperties);
        ReadData readData = new ReadData();
        List<RawMobileData> rawMobileDataList = readData.getData();
        try {
            System.out.println("Starting Sending Raw Mobiles at " + new Date());
            for (RawMobileData rawMobileData : rawMobileDataList) {
                ProducerRecord<String, RawMobileData> rawMobileDataProducerRecord = new ProducerRecord<>(mobilesTopic, rawMobileData.getName(), rawMobileData);
                Future<RecordMetadata> future = producer.send(rawMobileDataProducerRecord);
                RecordMetadata recordMetadata = future.get();
            }
            System.out.println("Sent " + rawMobileDataList.size() + " records at " + new Date());
        } finally {
            producer.close();
        }
    }

    private static Properties getMyProductTopicConfigProperties() {
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "com.kafka.project.grabber.serializers.RawMobileDataIdSerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.kafka.project.grabber.serializers.RawMobileDataSerializer");
        return configProperties;
    }


}
