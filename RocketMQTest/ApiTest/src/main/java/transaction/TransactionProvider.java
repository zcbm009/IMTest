package transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import topic.ConnectionConstraints;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 目的：使用RMQ来实现分布式事务
 * 情景：用户A向用户B转账300元，先发送一个半消息，事务完成后，再发送一个消息给消息服务，以发送消息，
 * 中间任何异常都会使事务回滚，且半消息将得到回滚的信号，然后撤消前面的半消息等待；
 * 如果消息服务器订阅消息后只得到了一个半消息，确认消息被丢失，那么消息服务需要对消息进行回查，查看上次的执行结果
 */
public class TransactionProvider {
    public static final String TRANSACTION_PROVIDER_GROUP = "transaction-provider-group";
    public static final Map<String, LocalTransactionState> MESSAGE_RESULT = new HashMap<>();
    public static final String TRANSACTION_TOPIC = "transaction-topic";

    public static void main(String[] args) throws MQClientException {

        TransactionMQProducer transactionMQProducer = new TransactionMQProducer(TRANSACTION_PROVIDER_GROUP);
        transactionMQProducer.setSendMsgTimeout(30 * 1000);
        transactionMQProducer.setNamesrvAddr(ConnectionConstraints.SRV_ADDRESS);
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                // 执行具体的业务逻辑，如果出现异常则回滚
                try {
                    // 如果成功后则提交，如果失败后则返回回滚消息，撤回半消息
                    System.out.println("用户A的账户减少3000元");
                    Thread.sleep(1000);
                    int i = 1/0;
                    System.out.println("用户B的账户增加3000元");
                    Thread.sleep(1000);

                    // 保存用户消息的处理结果以便回查
                    MESSAGE_RESULT.put(message.getTransactionId(), LocalTransactionState.COMMIT_MESSAGE);
                    return LocalTransactionState.COMMIT_MESSAGE;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    System.out.println("消息执行出现异常");
                    // 保存用户消息的处理结果以便回查
                    MESSAGE_RESULT.put(message.getTransactionId(), LocalTransactionState.ROLLBACK_MESSAGE);
                }
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                // 执行结果查询
                return MESSAGE_RESULT.get(messageExt.getTransactionId());
            }
        });
        transactionMQProducer.start();
        transactionMQProducer.sendMessageInTransaction(new Message(TRANSACTION_TOPIC,
                "用户A向用户B转账3000元".getBytes(StandardCharsets.UTF_8)), null);
//        transactionMQProducer.shutdown();
    }
}
