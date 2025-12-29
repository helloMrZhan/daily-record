package com.zjq.dailyrecord.excel.easyexcel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 测试数据模板表入参出参
 * <p>
 *
 * @author zjq
 * @description 测试数据模板
 */
public interface CeshiTemplateVo {

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @ApiModel(value = "测试数据模板表出参信息", description = "测试数据模板表出参信息")
    class QueryPage implements Serializable {

        @ExcelIgnore
        @ApiModelProperty("主键：时间戳+UUID转换数值后5位")
        private Long ceshiTemplateId;

        @ExcelIgnore
        @ApiModelProperty("测试数据模板唯一标识编码")
        private String ceshiTemplateCode;

        @ExcelProperty(value = "数据名称", index = 0)
        @ApiModelProperty("测试模板名称")
        private String ceshiTemplateName;


        @ExcelIgnore
        @ApiModelProperty(value = "数据所属机构")
        private Long organizeBy;

        @ExcelProperty(value = "所属机构", index = 4)
        @ApiModelProperty(value = "数据所属机构")
        private String organizeByName;

        @ExcelIgnore
        @ApiModelProperty("排序标识：展示（正序排序）,更新保存时间戳")
        private Long sortNum;

        @ExcelProperty(value = "数据描述", index = 1)
        @ApiModelProperty("业务描述")
        private String remark;
        

        @ExcelProperty(value = "创建时间", index = 2)
        @ApiModelProperty("创建时间")
        @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

        @ExcelProperty(value = "更新时间", index = 3)
        @ApiModelProperty("更新时间")
        @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updateTime;

        @ExcelIgnore
        @ApiModelProperty("是否本级机构,true-是，false-否")
        private boolean localInstitution = true;
    }
}
