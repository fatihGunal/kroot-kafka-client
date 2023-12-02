package com.tree.kroot.topicdomain.port;

import java.util.List;

public interface ConsumerPort {
    void consumeRecordsBy(List<String> topicNames);

    Integer getOffset();
}
