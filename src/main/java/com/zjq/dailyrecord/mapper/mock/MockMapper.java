package com.zjq.dailyrecord.mapper.mock;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MockMapper {

    /**
     * 查询模拟路由表名称
     * @param mockRoute 模拟路由
     * @return 路由表名称映射
     */
    Map<String, Object> queryMockRouteTableName(@Param("mockRoute") String mockRoute);

    /**
     * 查询模拟数据
     * @param mockTableName 表名称
     * @param queryWrapper 查询条件包装器
     * @return 模拟数据列表
     */
    List<Map<String, Object>> queryMockData(@Param("tableName") String mockTableName, @Param(Constants.WRAPPER) QueryWrapper<Map<String, Object>> queryWrapper);
}
