package icu.sunnyc.realtimelog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author ：hc
 * @date ：Created in 2022/2/6 10:57
 * @modified ：
 */
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                // 允许不同源访问
                .setAllowedOriginPatterns("*")
                // 有的浏览器不支持websocket，使用sockjs作为备选项
                .withSockJS();
    }

}
