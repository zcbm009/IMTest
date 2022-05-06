package com.test.im.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.im.dao.MessageDao;
import com.test.im.interceptor.MessageInterceptor;
import com.test.im.pojo.Message;
import com.test.im.pojo.User;
import com.test.im.pojo.UserMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler extends TextWebSocketHandler {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    // 模拟在线用户状态
    public static final Map<Long, WebSocketSession> messageSession = new HashMap<>();
    // 模拟当前连接用户状态
    private User user;
    public final MessageDao messageDao;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        Long userId = Long.valueOf((String) session.getAttributes().get(MessageInterceptor.UID_STR));
        user = UserMap.USER_MAP.get(userId);
        log.debug("连接用户：" + user);

        session.sendMessage(new TextMessage("欢迎接入， " + user.getName()));
        messageSession.put(user.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 1、解析接收的数据，接收的数据为json格式，{ "toId": 1111, "msg": "hello" }
        JsonNode jsonNode = OBJECT_MAPPER.readTree(message.getPayload());
        long toId = jsonNode.get("toId").asLong();
        String msg = jsonNode.get("msg").toString();
        // 2、保存消息数据
        Message buildMessage = Message.builder()
                .id(ObjectId.get())
                .from(user)
                .to(UserMap.USER_MAP.get(toId))
                .msg(msg)
                .build();
        messageDao.saveMessage(buildMessage);
        // 3、判断目标用户状态，如果目标用户在线，则发送消息后更改消息状态，如果目标用户不在线，则不做处理
        WebSocketSession toSession = messageSession.get(toId);
        if (toSession != null && toSession.isOpen()) {
            toSession.sendMessage(new TextMessage(buildMessage.toString()));
            messageDao.updateMessageState(buildMessage.getId(), 2);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        messageSession.remove(user.getId());
        log.debug("连接断开");
//        session.sendMessage(new TextMessage("再见，" + user.getName()));
    }
}
