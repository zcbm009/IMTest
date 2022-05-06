package transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import topic.ConnectionConstraints;

public class TransactionTopicCreate {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(TransactionProvider.TRANSACTION_PROVIDER_GROUP);
        defaultMQProducer.setNamesrvAddr(ConnectionConstraints.SRV_ADDRESS);
        defaultMQProducer.setSendMsgTimeout(30 * 1000);
        defaultMQProducer.start();
        defaultMQProducer.createTopic(ConnectionConstraints.BROKER_NAME, TransactionProvider.TRANSACTION_TOPIC, 4);
        System.out.println("Topic创建成功");
        defaultMQProducer.shutdown();
    }
}
