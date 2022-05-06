package com.suntao.test.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "boot-consumer-group",
        topic = "boot-topic",
        selectorExpression = "Async || Sync || Normal",
        consumeMode = ConsumeMode.CONCURRENTLY, // 异步模式获取消息
        messageModel = MessageModel.BROADCASTING // 集群模式为广播模式
)
public class NormalConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.debug("消息监听:" + Thread.currentThread().getId() + "--" + message);
        System.out.println("消息监听:" + Thread.currentThread().getId() + "--" + message);
    }
}
