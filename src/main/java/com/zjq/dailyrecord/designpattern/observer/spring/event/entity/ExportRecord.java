package com.zjq.dailyrecord.designpattern.observer.spring.event.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 导出记录表
 * @author zjq
 */
@Data
@TableName("export_record")
@ApiModel(description = "导出记录表")
public class ExportRecord {

    public static final String STATUS_PROCESS = "process";
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAIL = "fail";

    /** ID */
    @TableId
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("请求流水号")
    private String reqSerialNumber;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("导出类型")
    private String exportType;

    @ApiModelProperty("导出状态，process-处理中，success-成功，fail-失败")
    private String exportStatus;

    @ApiModelProperty("导出笔数")
    private Integer exportNum;

    @ApiModelProperty("完成时间")
    private Date completeTime;
}