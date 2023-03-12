package org.ws.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.ws.manager.WebSocketSessionManager;

import java.io.IOException;

@Slf4j
public abstract class WebSocketHandlerAdapter extends TextWebSocketHandler {
    public abstract WebSocketSessionManager getWebSocketManager();

    /**
     * 处理接收到客户端发送过来的信息
     *
     * @param session 会话
     * @param message 消息
     * @throws IOException ioexception
     */
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    }

    /**
     * socket 建立成功事件
     *
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        Object token = session.getAttributes().get("token");
        if (token == null) {
            log.warn("没有认证信息，拒绝连接!");
            return;
        }
        WebSocketSessionManager webSocketManager = getWebSocketManager();
        //已经建立连接关闭旧连接
        if (webSocketManager.getSession(token.toString()) != null) {
            log.warn("webSocketManager{},{} 重复建立连接", webSocketManager.getClass().getName(), token);
            webSocketManager.removeAndClose(token.toString());
        }
        // 用户连接成功，放入会话用户缓存
        webSocketManager.addSession(token.toString(), session);
        log.info("webSocketManager{},{} socket连接成功", webSocketManager.getClass().getName(), token);
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
            WebSocketSessionManager webSocketManager = getWebSocketManager();
            // 用户退出，移除缓存
            webSocketManager.removeAndClose(token.toString());
            log.info("webSocketManager{},{} 退出socket连接", webSocketManager.getClass().getName(), token);
        }
    }
}
