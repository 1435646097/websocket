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
 * @author buhao
 * @version WsSessionManager.java, v 0.1 2019-10-22 10:24 buhao
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
     * @param key
     */
    public void addSession(String key, WebSocketSession session) {
        // 添加 session
        getSessionStatePool().put(key, session);
    }


    /**
     * 删除并同步关闭连接
     *
     * @param key
     */
    public void removeAndClose(String key) throws IOException {
        WebSocketSession session = getSessionStatePool().remove(key);
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

    public Collection<WebSocketSession> getAllSession() {
        return getSessionStatePool().values();
    }
}
