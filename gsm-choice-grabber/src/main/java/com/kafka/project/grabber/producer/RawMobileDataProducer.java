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
import java.util.stream.Stream;

public class RawMobileDataProducer {

    private static final String mobilesTopic = "MOBILES";

    public static void main(String[] argv) throws Exception {
        Properties myProductTopicConfigProperties = getMyProductTopicConfigProperties();
        Producer producer = new KafkaProducer<>(myProductTopicConfigProperties);
        ReadData readData = new ReadData();
        List<RawMobileData> rawMobileDataList = readData.getData();
        try {
            List<Long> allTimes = new ArrayList<>();

            for (int i = 0; i < 50; i++) {
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
                allTimes.add(sendTime);
            }
            int averageAllTime = (int) allTimes.subList(10, 41).stream().mapToLong(a -> a).average().getAsDouble();
            int averageSerializationTime = (int) Stream.concat(RawMobileDataIdSerializer.serializationTime.stream(),
                    RawMobileDataSerializer.serializationTime.stream()).mapToLong(a -> a).sum() / 100;
            System.out.println("On average total time taken " + averageAllTime + " millis out of which average serialization time is " + averageSerializationTime + " millis.");

        } finally {
            producer.close();
        }
    }

    private static Properties getMyProductTopicConfigProperties() {
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.132.0.2:9092,10.132.0.3:9092,10.132.0.4:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "com.kafka.project.grabber.serializers.RawMobileDataIdSerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.kafka.project.grabber.serializers.RawMobileDataSerializer");
        configProperties.put(ProducerConfig.LINGER_MS_CONFIG, "200");
        configProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, String.valueOf(2500 * 2000));
        configProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "2147483648");
        return configProperties;
    }


}
