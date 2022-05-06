package consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import topic.ConnectionConstraints;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ConsumerDemo {
    public static final String CONSUMER_GROUP_NAME = "new-consumer-name";

    public static void main(String[] args) throws MQClientException {
        // 订阅topic，当消息队列中存在消息后，就会将消息推送这consumer，consumer调用监听器处理接收的消息
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        defaultMQPushConsumer.setNamesrvAddr(ConnectionConstraints.SRV_ADDRESS);

        // 第二个参数可添加tag，比如说监听topic名为new_topic而且tag为update的队列；第二个参数可以存在多个tag，多个tag之间使用|操作符号连接
        defaultMQPushConsumer.subscribe(ConnectionConstraints.NEW_TOPIC, "*");
        defaultMQPushConsumer.setMessageListener(
                (MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
                    // 调用会异步发生，如果存在多条消息同时存在会显示的比较混乱
                    list.forEach((ele) -> {
                        System.out.println("ele.getMsgId(): " + ele.getMsgId());
                        System.out.println("body: " + new String(ele.getBody(), StandardCharsets.UTF_8));
                        System.out.println("ele.getQueueId(): " + ele.getQueueId());
                        System.out.println("ele.getStoreTimestamp(): " + new Date(ele.getStoreTimestamp()));
                        System.out.println("---");
                    });
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                });
        defaultMQPushConsumer.start();
//        defaultMQPushConsumer.shutdown();


    }
}
