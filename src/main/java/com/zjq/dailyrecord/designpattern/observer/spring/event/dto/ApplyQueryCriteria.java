package com.zjq.dailyrecord.designpattern.observer.spring.event.dto;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import lombok.Data;

/**
 * 业务查询条件
 * @author zjq
 */
@Data
public class ApplyQueryCriteria {

    /** 客户名称 */
    private String clientName;


    /** 有效状态:0-未生效;1-有效;2-失效*/
    private String validFlag;

    /** 经办人 */
    private String createByName;

}