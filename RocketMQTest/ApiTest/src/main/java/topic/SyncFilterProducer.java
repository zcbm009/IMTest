package topic;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

public class SyncFilterProducer {

    public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        DefaultMQProducer mqProducer = new DefaultMQProducer(ConnectionConstraints.PRODUCER_GROUP_NAME);
        mqProducer.setNamesrvAddr(ConnectionConstraints.SRV_ADDRESS);
        mqProducer.setSendMsgTimeout(30 * 1000);

        Message message = new Message(ConnectionConstraints.NEW_TOPIC, ConnectionConstraints.TAG_NAME,
                "这是一个过滤器消息".getBytes(StandardCharsets.UTF_8));
        // 过滤条件
        message.putUserProperty("age", "208");
        message.putUserProperty("year", "19960");
        mqProducer.start();
        mqProducer.send(message);
        mqProducer.shutdown();
    }

}
