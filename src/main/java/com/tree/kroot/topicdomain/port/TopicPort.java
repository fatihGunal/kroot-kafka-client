package com.tree.kroot.topicdomain.port;

import org.apache.kafka.clients.admin.TopicDescription;

import java.util.Map;

public interface TopicPort {
    void createTopicGeneric(String topicName, int numPartitions, int replicationFactor);

    Map<String, TopicDescription> getTopicsResult();
}