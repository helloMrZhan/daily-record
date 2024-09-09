package com.zjq.dailyrecord.designpattern.observer.spring.event.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 共饮一杯无
 * @date 2024/9/9
 * @description: 业务传输对象
 */
@Data
public class ApplyDTO {

    @ApiModelProperty("客户名称")
    private String clientName;

    @ApiModelProperty("创建用户名称")
    private String createByName;


    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("授信起始时间")
    private Date creditBeginDate;

    @ApiModelProperty("授信完成时间")
    private Date creditEndDate;


    @ApiModelProperty("备注")
    private String remark;
}
