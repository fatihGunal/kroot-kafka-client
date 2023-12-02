package com.tree.kroot.infrastructure.kafka;

import com.tree.kroot.topicdomain.port.TopicPort;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class KafkaTopic implements TopicPort {

    private AdminClient admin;
    private final KafkaAdmin kafkaAdmin;
    private static final Logger logger = LoggerFactory.getLogger(KafkaTopic.class);

    @Autowired
    public KafkaTopic(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    @PostConstruct
    public void init() {
        this.admin = AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Override
    public void createTopicGeneric(String topicName, int numPartitions, int replicationFactor) {
        NewTopic newTopic = new NewTopic(topicName, numPartitions, (short) replicationFactor);
        CreateTopicsResult createTopicsResult = admin.createTopics(Collections.singleton(newTopic));

        try {
            createTopicsResult.all().get();
            logger.info("{} topic created", topicName);
        } catch (Exception e) {
            logger.error("Error while creating topic {}", topicName, e);
        }
    }

    @Override
    public Map<String, TopicDescription> getTopicsResult() {
        try {
            DescribeTopicsResult describeTopicsResult = admin.describeTopics(admin.listTopics().names().get());
            Map<String, TopicDescription> topicDescriptionMap = describeTopicsResult.all().get();
            topicDescriptionMap.forEach((key, value) -> logger.info("{}: {}", key, value));
            return topicDescriptionMap;
        } catch (Exception e) {
            logger.error("Error while getting topics", e);
            return Collections.emptyMap();
        }
    }
}
