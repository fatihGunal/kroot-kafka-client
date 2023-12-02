package com.tree.kroot.topicdomain.port;

public interface ProducerPort {
    void sendMessage(String topicName, String msg);
}
