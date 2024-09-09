package com.zjq.dailyrecord.designpattern.observer.spring.event.events;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 共饮一杯无
 * @date 2024/9/9
 * @description: 消息事件
 */
@Data
@AllArgsConstructor
public class MsgEvent {

    /**
     * 该类型事件携带的信息
     */
    private Long exportRecordId;

}
