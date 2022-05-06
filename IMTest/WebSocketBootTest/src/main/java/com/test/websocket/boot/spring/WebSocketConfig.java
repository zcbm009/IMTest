package com.test.websocket.boot.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MyWebSocketHandler myWebSocketHandler;
    private final MyHandlerInterceptor myHandlerInterceptor;

    @Autowired
    public WebSocketConfig(MyWebSocketHandler myWebSocketHandler, MyHandlerInterceptor myHandlerInterceptor) {
        this.myWebSocketHandler = myWebSocketHandler;
        this.myHandlerInterceptor = myHandlerInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册handler绑定请求链接并允许跨域
        registry.addHandler(myWebSocketHandler, "/ws").setAllowedOriginPatterns("*")
                .addInterceptors(myHandlerInterceptor);
    }


}
