package icu.sunnyc.realtimelog;

import icu.sunnyc.realtimelog.entity.LoggerMessage;
import icu.sunnyc.realtimelog.queues.LogQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * @author hczs8
 *
 * EnableWebSocketMessageBroker 启动websocket服务
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableWebSocketMessageBroker
public class RealTimeLogApplication {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RealTimeLogApplication.class, args);
    }

    int info = 0;

    @Scheduled(fixedRate = 1000)
    public void outputLogger() {
        log.info("测试日志输出" + info++);
    }

    /**
     * 向客户端推送log
     * 就是从LogQueue中取出日志发送到客户端
     * 执行顺序
     * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     * PostConstruct只会被调用一次
     */
    @PostConstruct
    public void pushLogs() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                1,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new AbortPolicy());
        threadPoolExecutor.submit(() -> {
            while (true) {
                LoggerMessage loggerMessage = LogQueue.getInstance().take();
                if (loggerMessage != null && messagingTemplate != null) {
                    messagingTemplate.convertAndSend("/topic/pullLogger", loggerMessage);
                }
            }
        });
    }

}
