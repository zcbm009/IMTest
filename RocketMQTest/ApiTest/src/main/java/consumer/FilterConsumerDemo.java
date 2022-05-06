package consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import topic.ConnectionConstraints;

public class FilterConsumerDemo {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer(ConsumerDemo.CONSUMER_GROUP_NAME);
        mqPushConsumer.setNamesrvAddr(ConnectionConstraints.SRV_ADDRESS);

        // 根据添加查询语句的过滤器监听队列
        mqPushConsumer.subscribe(ConnectionConstraints.NEW_TOPIC, MessageSelector.bySql("age > 18 AND year <= 1997"));
        mqPushConsumer.setMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            list.forEach(System.out::println);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        mqPushConsumer.start();
    }
}
