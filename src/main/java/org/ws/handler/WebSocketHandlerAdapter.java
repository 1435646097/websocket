package org.ws.handler;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.ws.manager.WebSocketSessionManager;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Liao.Ximing
 * @date 2023/03/13
 */
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
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    }

    /**
     * socket 建立成功事件
     *
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        String sessionId = this.getSessionId(session);
        if (sessionId == null) {
            this.closeSession(session);
            log.warn("{},未获取到SessionId，退出WebSocket连接",session.getUri());
            return;
        }
        WebSocketSessionManager webSocketManager = getWebSocketManager();
        //已经建立连接关闭旧连接
        if (webSocketManager.getSession(sessionId) != null) {
            log.warn("webSocketManager{},{} 重复建立连接", webSocketManager.getClass().getName(), sessionId);
            webSocketManager.removeAndClose(sessionId);
        }
        // 用户连接成功，放入会话用户缓存
        webSocketManager.addSession(sessionId, session);
        log.info("webSocketManager{},{} socket连接成功", webSocketManager.getClass().getName(), sessionId);
    }


    /**
     * 获取sessionId
     * @param session 会话连接
     * @return {@link String}
     */
    protected String getSessionId(WebSocketSession session){
        Object id = session.getAttributes().get("id");
        if (Objects.isNull(id)) {
            return CharSequenceUtil.EMPTY;
        }
        return id.toString();
    }

    /**
     * 关闭连接
     * @param session webSocket会话
     */
    protected void closeSession(WebSocketSession session) throws IOException {
        if (session.isOpen()) {
            session.close();
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
        String sessionId = this.getSessionId(session);
        if (sessionId != null) {
            WebSocketSessionManager webSocketManager = getWebSocketManager();
            // 用户退出，移除缓存
            webSocketManager.removeAndClose(sessionId);
            log.info("webSocketManager{},{} 退出socket连接", webSocketManager.getClass().getName(), sessionId);
        }
    }
}
