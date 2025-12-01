package com.zjq.dailyrecord.controller.mock;

import com.zjq.dailyrecord.service.mock.MockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * mock API
 *
 * @author zjq
 */
@Slf4j
@RestController
@RequestMapping
public class MockController {

    @Resource
    private MockService mockService;

    @GetMapping("/interface/**")
    public Object queryDataByGet(@RequestParam HashMap<String, Object> paramMap, HttpServletRequest request) {
        return mockService.queryData(request.getRequestURI(), paramMap);
    }

    @PostMapping("/interface/**")
    public Object queryDataByPost(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) {
        return mockService.queryData(request.getRequestURI(), paramMap);
    }
}