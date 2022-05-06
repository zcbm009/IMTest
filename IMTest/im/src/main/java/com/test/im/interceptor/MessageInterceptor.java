package com.test.im.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
@Component
public class MessageInterceptor implements HandshakeInterceptor {

    public static final String UID_STR = "uid";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 握手前
        String path = request.getURI().getPath();
        String[] pathSplit = path.split("/");
        if (pathSplit.length != 3) {
            return false;
        }
        if (!StringUtils.isNumeric(pathSplit[2])) {
            return false;
        }
        attributes.put(UID_STR, pathSplit[2]);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // 握手后
    }

}
