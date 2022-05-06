package topic;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * 创建topic的测试，创建topic的同时多个队列，向topic发送消息，消息会保存到对应的队列中
 */
public class TopicDemo {

    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(ConnectionConstraints.PRODUCER_GROUP_NAME);
        defaultMQProducer.setNamesrvAddr(ConnectionConstraints.SRV_ADDRESS);
//        defaultMQProducer.setSendMsgTimeout(60 * 1000);

        defaultMQProducer.start();
        defaultMQProducer.createTopic(ConnectionConstraints.BROKER_NAME, ConnectionConstraints.NEW_TOPIC, 8);
        System.out.println("创建成功");
        defaultMQProducer.shutdown();
    }
}
