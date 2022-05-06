package com.suntao.test.rocketmq.tx;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TxProducer {

    public final RocketMQTemplate rocketMQTemplate;

    public void sendTxMsg(String msg, String topic) {
        rocketMQTemplate.sendMessageInTransaction(topic, new GenericMessage<>(msg), null);
    }
}
