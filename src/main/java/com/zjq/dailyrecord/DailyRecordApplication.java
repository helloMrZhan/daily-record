package com.zjq.dailyrecord;

import cn.hutool.cron.CronUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

/**
 * 日常记录
 * @author zjq
 */
@SpringBootApplication
// 开启异步
@EnableAsync
public class DailyRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyRecordApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
