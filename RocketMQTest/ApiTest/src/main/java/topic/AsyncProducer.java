package topic;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

public class AsyncProducer {
    public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(ConnectionConstraints.PRODUCER_GROUP_NAME);
        defaultMQProducer.setNamesrvAddr(ConnectionConstraints.SRV_ADDRESS);
        defaultMQProducer.setSendMsgTimeout(30 * 1000);

        defaultMQProducer.start();
        defaultMQProducer.send(new Message(
                        ConnectionConstraints.NEW_TOPIC, ConnectionConstraints.TAG_NAME,
                        "这是另外一条异步消息".getBytes(StandardCharsets.UTF_8)),
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.println("异步消息发送成功！！！");
                        System.out.println("sendResult.getMsgId(): " + sendResult.getMsgId());
                        System.out.println("sendResult.getSendStatus(): " + sendResult.getSendStatus());
                        System.out.println("sendResult.getMessageQueue(): " + sendResult.getMessageQueue());

                        defaultMQProducer.shutdown();
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        System.out.println("异步消息发送失败");
                        throwable.printStackTrace();
                    }
                });
        // 异步发送消息不要调用shutdown，因为可能会在消息发送成功之前就被关闭了
    }
}
