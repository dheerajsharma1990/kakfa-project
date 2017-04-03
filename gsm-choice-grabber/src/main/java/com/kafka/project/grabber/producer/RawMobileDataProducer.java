package com.kafka.project.grabber.producer;

import com.kafka.project.grabber.ReadData;
import com.kafka.project.grabber.serializers.RawMobileDataIdSerializer;
import com.kafka.project.grabber.serializers.RawMobileDataSerializer;
import com.kafka.project.gsm.domain.RawMobileData;
import org.apache.kafka.clients.producer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class RawMobileDataProducer {

    private static final String mobilesTopic = "MOBILES";

    public static void main(String[] argv) throws Exception {
        Properties myProductTopicConfigProperties = getMyProductTopicConfigProperties();
        Producer producer = new KafkaProducer<>(myProductTopicConfigProperties);
        ReadData readData = new ReadData();
        List<RawMobileData> rawMobileDataList = readData.getData();
        List<RawMobileData> splitRawMobileDataList = rawMobileDataList.stream().map(RawMobileData::splitIntoThree).flatMap(List::stream).collect(Collectors.toList());
        try {
            long allTime = 0;
            for (int i = 0; i < 10; i++) {
                List<Future<RecordMetadata>> futures = new ArrayList<>();
                int count = 0;
                long startTime = System.currentTimeMillis();
                for (RawMobileData rawMobileData : rawMobileDataList) {
                    ProducerRecord<String, RawMobileData> rawMobileDataProducerRecord = new ProducerRecord<>(mobilesTopic, rawMobileData.getName(), rawMobileData);
                    futures.add(producer.send(rawMobileDataProducerRecord));
                }
                for (Future<RecordMetadata> future : futures) {
                    future.get();
                    count++;
                }
                long endTime = System.currentTimeMillis();
                long sendTime = endTime - startTime;
                System.out.println("Sent " + count + " records in " + sendTime + " millis.");
                allTime += sendTime;
            }
            long serializationTime = RawMobileDataIdSerializer.serializationTime.get() + RawMobileDataSerializer.serializationTime.get();
            System.out.println("On average total time taken " + (allTime / 10) + " millis out of which average serialization time " + (serializationTime / 10) + " millis.");

        } finally {
            producer.close();
        }
    }

    private static Properties getMyProductTopicConfigProperties() {
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "104.199.14.241:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "com.kafka.project.grabber.serializers.RawMobileDataIdSerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.kafka.project.grabber.serializers.RawMobileDataSerializer");
        configProperties.put(ProducerConfig.LINGER_MS_CONFIG, "100");
        configProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, String.valueOf(2500 * 5000));
        configProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "2147483648");
        return configProperties;
    }


}
