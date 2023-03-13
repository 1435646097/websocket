package org.ws.manager.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.ws.manager.WebSocketSessionManager;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Liao.Ximing
 * @date 2023/03/13
 */
@Component
public class DiagramWebSocketManger  extends WebSocketSessionManager {
    private ConcurrentHashMap<String, WebSocketSession> DIAGRAM_WEBSOCKET_STATE_POOL = new ConcurrentHashMap<>();
    @Override
    public ConcurrentHashMap<String, WebSocketSession> getSessionStatePool() {
        return DIAGRAM_WEBSOCKET_STATE_POOL;
    }
}
