package org.ws.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.ws.handler.impl.TtsWebSocketHandler;
import org.ws.interceptor.AuthenticationInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private TtsWebSocketHandler ttsHandler;
    @Autowired
    private AuthenticationInterceptor authInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(ttsHandler, "myWS")
                .addInterceptors(authInterceptor)
                .setAllowedOriginPatterns("*");
    }
}
