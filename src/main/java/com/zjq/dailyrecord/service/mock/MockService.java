package com.zjq.dailyrecord.service.mock;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjq.dailyrecord.mapper.mock.MockMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 模拟数据服务
 * @author: zjq
 */
@Slf4j
@Service
public class MockService {

    @Resource
    private MockMapper coreMapper;

    public String queryData(String mockRoute, Map paramMap) {
        Map map = coreMapper.queryMockRouteTableName(mockRoute);
        if (CollectionUtil.isEmpty(map)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 1);
            jsonObject.put("msg", "mock数据路由地址不存在");
            return jsonObject.toJSONString();
        }
        String mockType = MapUtil.getStr(map, "mock_type");
        Integer milliseconds = MapUtil.getInt(map, "sleep_time");
        if (milliseconds != null && milliseconds.intValue() > 0) {
            try {
                log.info("休眠时间-{}，单位毫秒", milliseconds);
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if ("1".equals(mockType)) {
            return queryData4Table(map, paramMap);
        } else if ("2".equals(mockType)) {
            return queryData4Json(map, paramMap);
        } else {
            JSONObject jsonObject = new JSONObject();

            JSONObject jsonObjectDatas = new JSONObject();
            jsonObject.put("code", 1);
            jsonObject.put("msg", "mock数据Type类型错误");
            jsonObject.put("data", jsonObjectDatas);
            return jsonObject.toJSONString();
        }
    }

    private String queryData4Table(Map map, Map paramMap) {
        String mockTableName = MapUtil.getStr(map, "mock_table_name");
        String requestParams = MapUtil.getStr(map, "request_params");
        Map<String, Object> requestParamsMap = JSONObject.parseObject(requestParams).getInnerMap();

        QueryWrapper queryWrapper = buildQueryWrapper(requestParamsMap, paramMap);
        log.info("查询表{}，参数信息{}", mockTableName, queryWrapper.getCustomSqlSegment());
        List<Map> list = coreMapper.queryMockData(mockTableName, queryWrapper);

        JSONObject jsonObject = new JSONObject();

        for (Map map1 : list) {
            Integer code = MapUtil.getInt(map1, "code");
            String msg = MapUtil.getStr(map1, "msg");
            if (code != null && code != 0 && StringUtils.isNotBlank(msg)) {
                jsonObject.put("code", code);
                jsonObject.put("msg", msg);
                jsonObject.put("data", "");
                return jsonObject.toJSONString();
            }
        }

        List datas = new ArrayList();
        datas.addAll(list);
        JSONObject jsonObjectDatas = new JSONObject();
        jsonObjectDatas.put("totalCount", list.size());
        jsonObjectDatas.put("datas", datas);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "操作成功");
        jsonObject.put("data", jsonObjectDatas);
        return jsonObject.toJSONString();
    }

    private String queryData4Json(Map map, Map paramMap) {
        String mockTableName = MapUtil.getStr(map, "mock_table_name");
        String requestParams = MapUtil.getStr(map, "request_params");
        Map<String, Object> requestParamsMap = JSONObject.parseObject(requestParams).getInnerMap();

        QueryWrapper queryWrapper = buildQueryWrapper(requestParamsMap, paramMap);
        log.info("查询表{}，参数信息{}", mockTableName, queryWrapper.getCustomSqlSegment());
        List<Map> list = coreMapper.queryMockData(mockTableName, queryWrapper);

        for (Map map1 : list) {
            String resultType = MapUtil.getStr(map1, "result_type");
            String resultJson = MapUtil.getStr(map1, "result_json");
            if ("object".equals(resultType)) {
                return resultJson;
            } else if ("array".equals(resultType)) {
                return resultJson;
            }
        }

        List resultList = new ArrayList(list.size());
        JSONObject jsonObject = new JSONObject();
        List datas = new ArrayList();
        datas.addAll(resultList);
        JSONObject jsonObjectDatas = new JSONObject();
        jsonObjectDatas.put("totalCount", resultList.size());
        jsonObjectDatas.put("datas", datas);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "操作成功");
        jsonObject.put("data", jsonObjectDatas);
        return jsonObject.toJSONString();
    }

    /**
     * 构建查询条件
     *
     * @return
     */
    private QueryWrapper buildQueryWrapper(Map<String, Object> requestParamsMap, Map<String, Object> paramMap) {
        QueryWrapper queryWrapper = new QueryWrapper();

        if (CollectionUtil.isNotEmpty(requestParamsMap)) {
            requestParamsMap.forEach((k, v) -> {
                queryWrapper.eq(v, paramMap.get(k));
            });
        }
        return queryWrapper;
    }
}
