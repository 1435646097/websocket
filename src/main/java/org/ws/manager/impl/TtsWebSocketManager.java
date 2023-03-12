package org.ws.manager.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.ws.manager.WebSocketSessionManager;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TtsWebSocketManager extends WebSocketSessionManager {
    private ConcurrentHashMap<String, WebSocketSession> TTS_WEBSOCKET_STATE_POOL = new ConcurrentHashMap<>();

    @Override
    public ConcurrentHashMap<String, WebSocketSession> getSessionStatePool() {
        return TTS_WEBSOCKET_STATE_POOL;
    }
}
