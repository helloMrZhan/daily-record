package com.zjq.dailyrecord.designpattern.observer.spring.event.events;

import lombok.*;
import org.springframework.context.ApplicationEvent;

/**
 * @author 共饮一杯无
 * @date 2024/9/9
 * @description: 文件导出事件
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class FileExportEventEvent extends ApplicationEvent {

    private Class dtoClass;

    private Class queryClass;

    private Object criteria;

    private Long exportRecordId;

    public FileExportEventEvent(Object source) {
        super(source);
    }

}
