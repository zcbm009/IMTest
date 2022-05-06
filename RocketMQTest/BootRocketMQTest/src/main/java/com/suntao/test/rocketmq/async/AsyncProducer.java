package com.suntao.test.rocketmq.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncProducer {

    public final RocketMQTemplate rocketMQTemplate;

    public void sendAsyncMessage(String msg, String topic) {
        rocketMQTemplate.asyncSend(topic, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.debug("消息发送成功");
                log.debug("发送结果:" + sendResult);
                System.out.println("消息发送成功");
                System.out.println(sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.warn("消息发送异常");
                System.out.println("消息发送异常");
                throwable.printStackTrace();
            }
        });
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
