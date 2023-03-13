/*
 * *
 *  * blog.coder4j.cn
 *  * Copyright (C) 2016-2019 All Rights Reserved.
 *
 */
package org.ws.interceptor;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 * @author Liao.Ximing
 * @date 2023/03/13
 */
@Component
@Slf4j
public class AuthenticationInterceptor implements HandshakeInterceptor {
    /**
     * 握手前
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        log.info("握手开始");
        // 获得请求参数
        Map<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), StandardCharsets.UTF_8);
        String sessionId = paramMap.get("username");
        if (CharSequenceUtil.isBlank(sessionId)) {
            log.warn("未发现token,连接失败");
            return false;
        }
        if (CharSequenceUtil.isNotBlank(sessionId)) {
            // 放入属性域
            attributes.put("id", sessionId);
            log.info("用户 sessionId {} 握手成功！", sessionId);
            return true;
        }
        log.info("用户登录已失效");
        return false;
    }

    /**
     * 握手后
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("握手完成");
    }
}
