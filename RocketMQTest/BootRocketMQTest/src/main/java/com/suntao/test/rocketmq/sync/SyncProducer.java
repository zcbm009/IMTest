package com.suntao.test.rocketmq.sync;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SyncProducer {

    public final RocketMQTemplate rocketMQTemplate;

    public SendResult sendSyncMsg(String msg, String topic) {
        return rocketMQTemplate.syncSend(topic, msg, 30 * 1000);
    }

}
