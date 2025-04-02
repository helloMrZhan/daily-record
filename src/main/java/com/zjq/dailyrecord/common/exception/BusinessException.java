package com.zjq.dailyrecord.common.exception;

/**
 * 业务异常封装类
 *
 * @author zjq
 * @date 20230518
 */
public class BusinessException extends RuntimeException{
    /**
     * 异常码
     */
    private int code;

    /**
     * 异常参数
     */
    private Object[] args;

    public BusinessException(int code) {
        this.code = code;
    }

    public BusinessException(int code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object[] getArgs() {
        if(args != null) {
            return args.clone();
        }else {
            return null;
        }

    }

    public void setArgs(Object[] args) {
        if(args != null) {
            this.args = args.clone();
        }
    }
}