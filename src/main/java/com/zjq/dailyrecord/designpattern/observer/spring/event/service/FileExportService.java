package com.zjq.dailyrecord.designpattern.observer.spring.event.service;

import com.zjq.dailyrecord.designpattern.observer.spring.event.dto.ApplyDTO;
import com.zjq.dailyrecord.designpattern.observer.spring.event.events.FileExportEventEvent;
import com.zjq.dailyrecord.designpattern.observer.spring.event.events.MsgEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author 共饮一杯无
 * @date 2024/9/9
 * @description: 文件导出服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileExportService {

    /**
     * 注入ApplicationEventPublisher用来发布事件
     */
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 导出记录文件
     *
     * @param exportRecordId 导出记录ID
     */
    public void exportRecord(Long exportRecordId) {
        long start = System.currentTimeMillis();

        // 1.导出附件 （同步处理）
        FileExportEventEvent event = new FileExportEventEvent(this);
        event.setDtoClass(ApplyDTO.class);
        event.setQueryClass(ApplyExcelQueryServiceImpl.class);
        event.setExportRecordId(exportRecordId);
        applicationEventPublisher.publishEvent(event);

        // 2.消息通知（异步处理）
        applicationEventPublisher.publishEvent(new MsgEvent(exportRecordId));

        long end = System.currentTimeMillis();
        log.info("导出记录，消息通知任务完成，总耗时：({})毫秒", end - start);
    }
}
