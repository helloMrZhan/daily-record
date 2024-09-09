package com.zjq.dailyrecord.designpattern.observer.spring.event.listener;

import com.zjq.dailyrecord.designpattern.observer.spring.event.events.MsgEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 消息监听器
 * @author 共饮一杯无
 */
@Slf4j
@Component
public class MsgListener {

    @Async
    @SneakyThrows
    @EventListener(MsgEvent.class)
    public void sendMsg(MsgEvent event) {
        Long exportRecordId = event.getExportRecordId();
        long start = System.currentTimeMillis();
        log.info("开始发送短信...");
        log.info("开始发送邮件...");
        Thread.sleep(4000);
        long end = System.currentTimeMillis();
        log.info("附件导出记录ID:{}：发送短信、邮件耗时：({})毫秒", exportRecordId, (end - start));
    }
}
