package com.zjq.dailyrecord.common.exception;

/**
 * 异常构建类
 *
 * @author zjq
 * @date 20230518
 */
public class BusinessExceptionBuilder {

    /**
     * 构建code
     * @param code 异常码
     * @return
     */
    public static BusinessException build(int code){
        return new BusinessException(code);
    }

    /**
     * 构建code和args
     * @param code 异常码
     * @param args 异常参数
     * @return
     */
    public static BusinessException build(int code ,Object... args){
        return new BusinessException(code, args);
    }


}