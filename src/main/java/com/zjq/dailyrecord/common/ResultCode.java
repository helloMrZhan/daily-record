package com.zjq.dailyrecord.common;

public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "请求失败,系统遇到未知错误"),
    DELETE_FAILED(560, "删除失败，数据已存在"),
    FILE_NOT_FOUND(561, "文件未找到或不存在"),
    FILE_NAME_NOT_NULL(562, "编码的文件名不能为空"),
    BAD_REQUEST(400, "服务器未能理解请求"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    PAYMENT_REQUIRED(402, "此代码尚无法使用"),
    FORBIDDEN(403, "服务器拒绝执行,可能是没有相应权限"),
    NOT_FOUND(404, "请求资源未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不被允许"),
    VALIDATE_FAILED(460, "参数检验失败"),
    VALIDATE_NULL_FAILED(461, "必传参数为空值"),
    OPERATION_WARNING(600, "操作警告");

    private final Integer code;
    private final String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}