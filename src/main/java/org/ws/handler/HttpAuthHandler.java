/*
 * *
 *  * blog.coder4j.cn
 *  * Copyright (C) 2016-2019 All Rights Reserved.
 *
 */
package org.ws.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.ws.manager.WsSessionManager;

import java.util.Collection;

/**
 * @author buhao
 * @version MyWSHandler.java, v 0.1 2019-10-17 17:10 buhao
 */
@Component
@Slf4j
public class HttpAuthHandler extends TextWebSocketHandler {

    /**
     * socket 建立成功事件
     *
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Object token = session.getAttributes().get("token");
        if (token != null) {
            // 用户连接成功，放入在线用户缓存
            WsSessionManager.add(token.toString(), session);
            log.info("socket连接成功");
        } else {
            throw new RuntimeException("用户登录已经失效!");
        }
    }

    /**
     * 接收消息事件
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
//        String payload = message.getPayload();
//        Object token = session.getAttributes().get("token");
//        System.out.println("server 接收到 " + token + " 发送的 " + payload);
//        session.sendMessage(new TextMessage("server 发送给 " + token + " 消息 " + payload + " " + LocalDateTime.now().toString()));
        Collection<WebSocketSession> allWebSocket = WsSessionManager.getAll();
        for (WebSocketSession webSocketSession : allWebSocket) {
            webSocketSession.sendMessage(new TextMessage("都给我接收"));
        }
    }

    /**
     * socket 断开连接时
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object token = session.getAttributes().get("token");
        if (token != null) {
            // 用户退出，移除缓存
            WsSessionManager.remove(token.toString());
            log.info("退出socket连接");
        }
    }
}
