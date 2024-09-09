package com.zjq.dailyrecord.designpattern.observer.spring.event.service;

import com.zjq.dailyrecord.designpattern.observer.spring.event.dto.ApplyDTO;
import com.zjq.dailyrecord.designpattern.observer.spring.event.dto.ApplyQueryCriteria;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 业务数据导出
 * @author zjq
 */
@Service
@AllArgsConstructor
public class ApplyExcelQueryServiceImpl implements IExportQueryService {

    private final IRegistrationService registrationService;

    @Override
    public List<Map<String, Object>> queryForExcel(Object criteria) {
        ApplyQueryCriteria queryCriteria = (ApplyQueryCriteria) criteria;
        List<ApplyDTO> dtos = new ArrayList<>();
        return convertToDownloadMap(dtos);
    }

    @Override
    @PostConstruct
    public void doRegister() {
        registrationService.doRegister(ApplyDTO.class, ApplyExcelQueryServiceImpl.class);
    }

    private List<Map<String, Object>> convertToDownloadMap(List<ApplyDTO> dtos) {
        List<Map<String, Object>> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ApplyDTO applydto : dtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("客户名称", applydto.getClientName());
            map.put("授信起始时间", applydto.getCreditBeginDate()!=null?sdf.format(applydto.getCreditBeginDate()):"-");
            map.put("授信到期时间", applydto.getCreditEndDate()!=null?sdf.format(applydto.getCreditEndDate()):"-");
            map.put("经办人", applydto.getCreateByName());
            map.put("创建时间", applydto.getCreateTime());
            map.put("备注",applydto.getRemark());
            list.add(map);
        }
        return list;
    }
}