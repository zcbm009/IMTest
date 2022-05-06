package com.test.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 1、常用API
 * 2、发送消息
 * 3、PathParam截取
 */
@ServerEndpoint("/websocket/{uid}")
public class MyWebSocket {

    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) throws IOException {
        System.out.println("连接建立:" + session + "-" + uid);
        session.getBasicRemote().sendText("欢迎登录系统");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("收到客户端发送的消息" + message);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        session.getBasicRemote().sendText("消息已接收到");
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("连接关闭" + session);
    }

}
