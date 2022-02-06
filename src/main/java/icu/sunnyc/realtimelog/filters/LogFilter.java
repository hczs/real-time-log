package icu.sunnyc.realtimelog.filters;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import icu.sunnyc.realtimelog.entity.LoggerMessage;
import icu.sunnyc.realtimelog.queues.LogQueue;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author ：hc
 * @date ：Created in 2022/2/6 15:00
 * @modified ：
 *
 * 日志过滤器
 * 作用：全局日志都封装为LoggerMessage放入LogQueue中
 */
@Slf4j
public class LogFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        LoggerMessage loggerMessage = new LoggerMessage(
                event.getMessage()
                , DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())),
                event.getThreadName(),
                event.getLoggerName(),
                event.getLevel().levelStr
        );
        // 日志信息入队
        LogQueue.getInstance().put(loggerMessage);
        return FilterReply.ACCEPT;
    }
}
