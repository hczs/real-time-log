package icu.sunnyc.realtimelog.config;

import icu.sunnyc.realtimelog.entity.LoggerMessage;
import icu.sunnyc.realtimelog.queues.LogQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

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
