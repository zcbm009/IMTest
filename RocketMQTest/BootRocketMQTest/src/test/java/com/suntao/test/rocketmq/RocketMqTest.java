package com.suntao.test.rocketmq;

import com.suntao.test.rocketmq.async.AsyncProducer;
import com.suntao.test.rocketmq.normal.NormalProducer;
import com.suntao.test.rocketmq.sync.SyncProducer;
import com.suntao.test.rocketmq.tx.TxProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RocketMqTest {

    @Autowired
    private AsyncProducer asyncProducer;
    @Autowired
    private NormalProducer normalProducer;
    @Autowired
    private SyncProducer syncProducer;
    @Autowired
    private TxProducer txProducer;

    private final String TOPIC = "boot-topic";

    @Test
    public void testSyncMsgSend() {
        SendResult sendResult = syncProducer.sendSyncMsg("发送一个同步消息", TOPIC + ":Sync");
        System.out.println("发送消息成功");
        System.out.println("MsgId:" + sendResult.getMsgId());
        System.out.println("TransactionId:" + sendResult.getTransactionId());
        System.out.println("SendStatus" + sendResult.getSendStatus().toString());
        System.out.println("QueueId" + sendResult.getMessageQueue().getQueueId());
    }

    @Test
    public void testAsyncMsgSend() {
        asyncProducer.sendAsyncMessage("发送一个异步消息", TOPIC + ":Async");
    }

    @Test
    public void testNormalMsgSend() {
        normalProducer.sendMessage("发送一个普通消息", TOPIC + ":Normal");
    }

    @Test
    public void testTxMsgSend() {
        txProducer.sendTxMsg("发送一个事务消息", TOPIC + ":Tx");
        try {
            Thread.sleep(300 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
