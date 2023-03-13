/*
 * *
 *  * blog.coder4j.cn
 *  * Copyright (C) 2016-2019 All Rights Reserved.
 *
 */
package org.ws.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Liao.Ximing
 * @date 2023/03/13
 */
@Slf4j
public abstract class WebSocketSessionManager {
    /**
     * 保存session状态池
     *
     * @return {@link ConcurrentHashMap}<{@link String}, {@link WebSocketSession}>
     */
    public abstract ConcurrentHashMap<String, WebSocketSession> getSessionStatePool();
    /**
     * 添加 session
     *
     * @param sessionId 会话id
     */
    public void addSession(String sessionId, WebSocketSession session) {
        // 添加 session
        getSessionStatePool().put(sessionId, session);
    }


    /**
     * 删除并同步关闭连接
     *
     * @param sessionId 会话id
     */
    public void removeAndClose(String sessionId) throws IOException {
        WebSocketSession session = getSessionStatePool().remove(sessionId);
        if (session != null && session.isOpen()) {
            // 关闭连接
            session.close();
        }
    }
    /**
     * 获得 session
     *
     * @param key
     * @return
     */
    public WebSocketSession getSession(String key) {
        // 获得 session
        return getSessionStatePool().get(key);
    }

    /**
     * 获取所有WebSocket会话
     * @return {@link Collection}<{@link WebSocketSession}>
     */
    public Collection<WebSocketSession> getAllSession() {
        return getSessionStatePool().values();
    }
}
