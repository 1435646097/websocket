/*
 * *
 *  * blog.coder4j.cn
 *  * Copyright (C) 2016-2019 All Rights Reserved.
 *
 */
package org.ws.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.ws.handler.WebSocketHandlerAdapter;
import org.ws.manager.WebSocketSessionManager;
import org.ws.manager.impl.TtsWebSocketManager;

import java.io.IOException;
import java.util.Collection;


/**
 * @author Liao.Ximing
 * @date 2023/03/13
 */
@Component
@Slf4j
public class TtsWebSocketHandler extends WebSocketHandlerAdapter {
    @Autowired
    TtsWebSocketManager ttsWebSocketManager;

    @Override
    public WebSocketSessionManager getWebSocketManager() {
        return ttsWebSocketManager;
    }

    /**
     * 接收消息事件
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        log.info("获取到了用户的消息：{}", message);
        Collection<WebSocketSession> allWebSocket = ttsWebSocketManager.getAllSession();
        for (WebSocketSession webSocketSession : allWebSocket) {
            String userName = session.getAttributes().getOrDefault("token", "").toString();
            webSocketSession.sendMessage(new TextMessage(userName + "发送:" + message.getPayload()));
        }
    }

}
