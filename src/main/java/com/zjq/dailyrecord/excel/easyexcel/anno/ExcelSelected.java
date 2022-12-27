package com.zjq.dailyrecord.excel.easyexcel.anno;

import com.zjq.dailyrecord.excel.easyexcel.service.ExcelDynamicSelect;

import java.lang.annotation.*;

/**
 * 标注导出的列为下拉框类型，并为下拉框设置内容
 * @author zjq
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelSelected {
    /**
     * 固定下拉内容
     */
    String[] source() default {};

    /**
     * 动态下拉内容
     */
    Class<? extends ExcelDynamicSelect>[] sourceClass() default {};

    /**
     * 设置下拉框的起始行，默认为第二行
     */
    int firstRow() default 1;

    /**
     * 设置下拉框的结束行，默认为最后一行
     */
    int lastRow() default 0x10000;
}
