package com.tree.kroot.topicdomain.service;

import com.tree.kroot.topicdomain.port.TopicPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DomainTopicServiceImpl implements TopicService {

    private final TopicPort topicPort;

    @Value(value = "${spring.kafka.number-partition}")
    Integer numPartitions;
    @Value(value = "${spring.kafka.replication-factor}")
    Integer replicationFactor;

    @Autowired
    public DomainTopicServiceImpl(TopicPort topicPort) {
        this.topicPort = topicPort;
    }

    @Override
    public String createTopicGeneric(String topicName) {
        topicPort.createTopicGeneric(topicName, numPartitions, replicationFactor);
        return "Topic Created !";
    }
}