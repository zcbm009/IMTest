package com.suntao.test.rocketmq.normal;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NormalProducer {

    private final RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String msg, String topic) {
        rocketMQTemplate.convertAndSend(topic, msg);
    }
}
