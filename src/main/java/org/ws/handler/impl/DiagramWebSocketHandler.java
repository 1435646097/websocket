package org.ws.handler.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.ws.handler.WebSocketHandlerAdapter;
import org.ws.manager.WebSocketSessionManager;
import org.ws.manager.impl.DiagramWebSocketManger;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author Liao.Ximing
 * @date 2023/03/13
 */
@Component
public class DiagramWebSocketHandler extends WebSocketHandlerAdapter {
    @Autowired
    DiagramWebSocketManger diagramWebSocketManger;

    @Override
    public WebSocketSessionManager getWebSocketManager() {
        return diagramWebSocketManger;
    }

    @Override
    protected String getSessionId(WebSocketSession session) {
        URI uri = session.getUri();
        if (Objects.isNull(uri)) {
            return CharSequenceUtil.EMPTY;
        }
        // 获得请求参数
        Map<String, String> paramMap = HttpUtil.decodeParamMap(uri.getQuery(), StandardCharsets.UTF_8);
        return paramMap.get("diagramId");
    }
}
