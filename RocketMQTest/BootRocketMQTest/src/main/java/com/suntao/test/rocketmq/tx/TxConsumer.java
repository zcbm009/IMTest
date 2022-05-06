package com.suntao.test.rocketmq.tx;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * 这应该是生产者应该执行的过程，在测试过程中应该保持连接以便客户端回查
 */
@RocketMQTransactionListener
@Slf4j
public class TxConsumer implements RocketMQLocalTransactionListener {
    public static final Map<String, RocketMQLocalTransactionState> MSG_RESULT = new HashMap<>();
    public static final String TRX_KEY = RocketMQHeaders.PREFIX + RocketMQHeaders.TRANSACTION_ID;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        String txId = (String) message.getHeaders().get(TRX_KEY);
        try {
            log.debug("txId:" + txId);
            Object payload = message.getPayload();
            System.out.println("txId:" + txId);
            System.out.println("获取到事务消息" + payload);

            System.out.println("执行第一个过程");
            Thread.sleep(5 * 1000);
            System.out.println("执行第二个过程");
            Thread.sleep(5 * 1000);
            System.out.println("执行第三个过程");
            Thread.sleep(5 * 1000);

            MSG_RESULT.put(txId, RocketMQLocalTransactionState.COMMIT);

            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
        }

        MSG_RESULT.put(txId, RocketMQLocalTransactionState.ROLLBACK);
        return RocketMQLocalTransactionState.ROLLBACK;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String txId = (String) message.getHeaders().get(TRX_KEY);
        assert txId != null;
        RocketMQLocalTransactionState state = MSG_RESULT.get(txId);
        System.out.println("消息回查:" + txId + "--" + state);
        return state;
    }
}
