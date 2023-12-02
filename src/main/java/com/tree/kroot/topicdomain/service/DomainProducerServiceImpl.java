package com.tree.kroot.topicdomain.service;

import com.tree.kroot.topicdomain.port.ProducerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainProducerServiceImpl implements ProducerService {

    private final ProducerPort producerPort;

    @Autowired
    public DomainProducerServiceImpl(ProducerPort producerPort) {
        this.producerPort = producerPort;
    }

    @Override
    public String producer(String topicName, String msg) {
        producerPort.sendMessage(topicName, msg);
        return "Produce Succes!";
    }
}