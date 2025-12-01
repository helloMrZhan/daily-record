package com.zjq.dailyrecord.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MybatisPlus配置
 * @author zjq
 */
@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = {"com.zjq.dailyrecord.**.mapper"})
public class MybatisPlusConfig {


}
