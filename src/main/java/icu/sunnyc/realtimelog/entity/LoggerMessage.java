package icu.sunnyc.realtimelog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：hc
 * @date ：Created in 2022/1/18 21:35
 * 日志消息体 - 实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggerMessage {

    /**
     * 日志体
     */
    private String body;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 线程名
     */
    private String threadName;

    /**
     * 类名
     */
    private String className;

    /**
     * 日志等级
     */
    private String level;

}
