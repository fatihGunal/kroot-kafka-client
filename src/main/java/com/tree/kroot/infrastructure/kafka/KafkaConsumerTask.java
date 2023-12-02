package com.tree.kroot.infrastructure.kafka;

import com.tree.kroot.topicdomain.port.ConsumerPort;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class KafkaConsumerTask implements ConsumerPort {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTopic.class);

    private final ConsumerFactory<String, String> consumerFactory;

    @Autowired
    public KafkaConsumerTask(ConsumerFactory<String, String> consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    // TODO: consume in the background if this is not the case. This should obv not block the program!
    @Override
    public void consumeRecordsBy(List<String> topicNames) {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerFactory.getConfigurationProperties())) {
            consumer.subscribe(topicNames);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    logger.info("Key: {}, Value: {}", record.key(), record.value());
                    logger.info("Partition: {}, Offset: {}", record.partition(), record.offset());
                }
            }
        } catch (Exception e) {
            logger.error("Error while consuming topic ", e);
        }
    }

    @Override
    public Integer getOffset() {
        return null;
    }
}