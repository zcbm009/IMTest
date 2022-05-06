package transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import topic.ConnectionConstraints;

public class TransactionConsumer {

    public static final String TRANSACTION_CONSUMER_GROUP = "transaction-consumer-group";

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer transactionConsumer = new DefaultMQPushConsumer(TRANSACTION_CONSUMER_GROUP);
        transactionConsumer.setNamesrvAddr(ConnectionConstraints.SRV_ADDRESS);
        transactionConsumer.setConsumeTimeout(30 * 1000);
        transactionConsumer.subscribe(TransactionProvider.TRANSACTION_TOPIC, "*");
        transactionConsumer.setMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            list.forEach(System.out::println);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        transactionConsumer.start();
//        transactionConsumer.shutdown();
    }
}
