package com.tree.kroot.topicdomain.service;

import com.tree.kroot.topicdomain.port.ConsumerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainConsumerServiceimpl implements ConsumerService {

    private final ConsumerPort consumerPort;

    @Autowired
    public DomainConsumerServiceimpl(ConsumerPort consumerPort) {
        this.consumerPort = consumerPort;
    }

    @Override
    public String consumer(List<String> topicNames) {
        consumerPort.consumeRecordsBy(topicNames);
        return "Consume Succes!";
    }
}