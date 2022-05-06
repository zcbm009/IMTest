package order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import topic.ConnectionConstraints;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class OrderProducer {
    public static final String GROUP_NAME = "order_message_group";
    public static final String MESSAGE_TOPIC = "order_message_topic";
    public static final String MESSAGE_TAG = "order_message_tag";

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
//        有序消息的测试与使用，核心内容在于生产者将同一类消息都发送到同一个队列中
        // 产生的效果：发送100条消息，分为10组，这10组的orderId是一样的，然后对orderId进行取模运算得到要发送的队列ID
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(GROUP_NAME);
        defaultMQProducer.setSendMsgTimeout(30 * 1000);
        defaultMQProducer.setNamesrvAddr(ConnectionConstraints.SRV_ADDRESS);
        defaultMQProducer.start();
        for (int i = 0; i < 100; i++) {
            int orderId = i % 10;
            // 添加新的topic，默认其队列数为4
            Message message = new Message(MESSAGE_TOPIC, MESSAGE_TAG,
                    ("这是一条消息，orderId为 " + orderId).getBytes(StandardCharsets.UTF_8));
            defaultMQProducer.send(message, (list, message1, o) -> {
                // 消息队列选择器，这里的参数o来自于send方法的第三个参数orderId，list代表topic存在的消息队列的列表
                Integer id = (Integer) o;
                int queueId = id % list.size();
                return list.get(queueId);
            }, orderId);
        }
//        defaultMQProducer.send()
        defaultMQProducer.shutdown();
    }
}
