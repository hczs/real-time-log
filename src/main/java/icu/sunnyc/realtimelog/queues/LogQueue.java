package icu.sunnyc.realtimelog.queues;

import icu.sunnyc.realtimelog.entity.LoggerMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ：hc
 * @date ：Created in 2022/2/6 11:00
 * @modified ：
 *
 * 自己封装一个存储日志的阻塞队列 - 单例 饿汉式
 */
@Slf4j
public class LogQueue {

    /**
     * 队列大小
     */
    public static final int QUEUE_MAX_SIZE = 10000;

    /**
     * 单例对象
     */
    private static final LogQueue LOG_QUEUE = new LogQueue();

    /**
     * 实际存储日志信息的阻塞队列
     * LinkedBlockingQueue 入队出队采用两把锁，入队出队互不干扰，效率较高
     */
    private final LinkedBlockingQueue<LoggerMessage> blockingQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    private LogQueue() {

    }

    public static LogQueue getInstance() {
        return LOG_QUEUE;
    }

    /**
     * 日志信息入队，如果队满会一直阻塞，直到可以放入数据，或者响应中断
     * @param loggerMessage 日志信息对象
     */
    public void put(LoggerMessage loggerMessage) {
        try {
            blockingQueue.put(loggerMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("日志信息入队，响应中断");
        }
    }

    /**
     * 日志信息出队，如果队空会一直阻塞，直到拿到数据，或者响应中断
     */
    public LoggerMessage take() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("日志信息出队，响应中断");
        }
        return null;
    }

}
