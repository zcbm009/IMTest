package topic;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

public class SyncProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(ConnectionConstraints.PRODUCER_GROUP_NAME);
        defaultMQProducer.setNamesrvAddr(ConnectionConstraints.SRV_ADDRESS);
        defaultMQProducer.setSendMsgTimeout(60 * 1000);
        defaultMQProducer.start();
        SendResult sendResult = defaultMQProducer.send(
                new Message(ConnectionConstraints.NEW_TOPIC, ConnectionConstraints.TAG_NAME,
                        "这是一个消息2".getBytes(StandardCharsets.UTF_8)));
        System.out.println("MsgId: " + sendResult.getMsgId());
        System.out.println("SendStatus: " + sendResult.getSendStatus());
        System.out.println("MessageQueue: " + sendResult.getMessageQueue());
        defaultMQProducer.shutdown();
    }
}
