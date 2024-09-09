package com.zjq.dailyrecord.designpattern.observer.spring.event.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.zjq.dailyrecord.designpattern.observer.spring.event.entity.ExportRecord;
import com.zjq.dailyrecord.designpattern.observer.spring.event.events.FileExportEventEvent;
import com.zjq.dailyrecord.designpattern.observer.spring.event.service.IExportQueryService;
import com.zjq.dailyrecord.designpattern.observer.spring.event.service.IRegistrationService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * 实现 ApplicationListener 接口，并指定监听的事件类型
 *
 * @author 共饮一杯无
 * @date 2024/9/9
 * @description
 */
@Slf4j
@Component
public class FileExportListener implements ApplicationListener<FileExportEventEvent> {

    private final IRegistrationService registrationService;

    public FileExportListener(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * 使用 onApplicationEvent 方法对消息进行接收处理
     */
    @SneakyThrows
    @Override
    public void onApplicationEvent(FileExportEventEvent event) {
        long start = System.currentTimeMillis();
        ExportRecord record = new ExportRecord();
        Assert.notNull(record, "没有查询到导出记录");

        try {
            // 1.获取导出类，导出记录
            Class queryClass = registrationService.getRegistry(event.getDtoClass());
            IExportQueryService exportQueryService = (IExportQueryService) SpringUtil.getBean(queryClass);

            // 2.获取导出的数据集
            List<Map<String, Object>> exportDataList = exportQueryService.queryForExcel(event.getCriteria());

            // 3.生成excel导出附件
            // 休眠2秒模拟业务处理
            Thread.sleep(2000);

        } catch (Exception e) {
            log.error("导出异常：{}", e);
            // 记录错误信息
            throw new Exception(e.getMessage());
        }
        long end = System.currentTimeMillis();
        log.info("记录ID：{}：导出耗时：({})毫秒", event.getExportRecordId(), (end - start));
    }
}
