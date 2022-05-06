package order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import topic.ConnectionConstraints;

import java.nio.charset.StandardCharsets;

public class OrderCustomer {
    public static final String GROUP_NAME = "order_consumer";

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(GROUP_NAME);
        defaultMQPushConsumer.setNamesrvAddr(ConnectionConstraints.SRV_ADDRESS);
        defaultMQPushConsumer.subscribe(OrderProducer.MESSAGE_TOPIC, OrderProducer.MESSAGE_TAG);
        defaultMQPushConsumer.setMessageListener((MessageListenerOrderly) (list, consumeOrderlyContext) -> {
            list.forEach(ele -> {
                String stringBuilder = Thread.currentThread().getId() + " MsgQueueId:" +
                        ele.getQueueId() + " body:" + new String(ele.getBody(), StandardCharsets.UTF_8);
                System.out.println(stringBuilder);
            });
            return ConsumeOrderlyStatus.SUCCESS;
        });
        defaultMQPushConsumer.start();
    }
}
