package com.zjq.dailyrecord.excel.easyexcel.mapper;

import com.zjq.dailyrecord.excel.easyexcel.service.ExcelDynamicSelect;
import com.zjq.dailyrecord.excel.easyexcel.util.SpringContextUtil;

import java.util.Arrays;

public class CityExcelSelectedImpl implements ExcelDynamicSelect {

    @Override
    public String[] getSource() {

        String[] citys = {"北京","上海","广州","深圳"};
        return citys;
    }

}
