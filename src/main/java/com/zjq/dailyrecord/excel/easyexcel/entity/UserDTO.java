package com.zjq.dailyrecord.excel.easyexcel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.zjq.dailyrecord.excel.easyexcel.anno.ExcelSelected;
import com.zjq.dailyrecord.excel.easyexcel.mapper.CityExcelSelectedImpl;

import java.util.Date;

public class UserDTO {
    @ExcelProperty(index = 0,value = "编号")
    private Long id;
    @ExcelProperty(index = 1,value = "姓名")
    private String name;
    @ExcelProperty(index = 2,value = "年龄")
    private Integer age;
    @ExcelProperty(index = 3,value = "性别")
    //静态下拉框
    @ExcelSelected(source = {"男","女"})
    private String sex;
    @ExcelProperty(index = 4,value = "出生日期")
    private Date birthday;
    @ExcelProperty(index = 5,value = "居住城市")
    //动态下拉框-去数据库中查询所有城市供其选择
    @ExcelSelected(sourceClass = CityExcelSelectedImpl.class)
    private String city;
    @ExcelIgnore
    private Boolean isDeleted;
}
