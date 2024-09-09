package com.zjq.dailyrecord.designpattern.observer.spring.event.service;

import java.util.List;
import java.util.Map;

/**
 * 导出查询服务
 * @author zjq
 */
public interface IExportQueryService {

    /**
     * 查询导出数据
     * @param criteria
     * @return
     */
    List<Map<String, Object>> queryForExcel(Object criteria);

    /**
     * 注册dto类与查询类的对应关系
     */
    void doRegister();
}