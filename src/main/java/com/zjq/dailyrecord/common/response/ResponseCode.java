package com.zjq.dailyrecord.common.response;

/**
 * 响应状态码
 *
 * @author zjq
 * @date 20230518
 */
public final class ResponseCode {

    /**
     * 请求成功
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 禁止访问
     */
    public static final int FORBIDDEN = 403;

    /**
     * 请求的资源不存在
     */
    public static final int NOT_FOUND = 404;

    /**
     * 请求方法不被允许
     */
    public static final int METHOD_NOT_ALLOWED = 405;

    /**
     * 请求超时
     */
    public static final int REQUEST_TIMEOUT = 408;

    /**
     * 内部服务器错误
     */
    public static final int INTERNAL_SERVER_ERROR = 500;

    /**
     * 服务不可用
     */
    public static final int SERVICE_UNAVAILABLE = 503;

    /**
     * 失败响应码
     */
    public static final int FAIL_CODE = 500;

    /**
     * 未查询到对象
     */
    public static final int RECORD_NO_EXIST = 5000001;
    

    /**
     * 违反唯一索引约束
     */
    public static final int UNIQUE_KEY = 5000005;

    /**
     * 存在多条记录
     */
    public static final int MULTI_RECORDS = 5000006;

    /**
     * 请求被拒绝
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 请求冲突
     */
    public static final int CONFLICT = 409;

    /**
     * 预期失败
     */
    public static final int EXPECTATION_FAILED = 417;

    /**
     * 请求实体过大
     */
    public static final int PAYLOAD_TOO_LARGE = 413;

    /**
     * 请求的URI过长
     */
    public static final int URI_TOO_LONG = 414;

    /**
     * 不支持的媒体类型
     */
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;

    /**
     * 未满足前提条件
     */
    public static final int PRECONDITION_FAILED = 412;

    /**
     * 请求范围不满足要求
     */
    public static final int RANGE_NOT_SATISFIABLE = 416;

    /**
     * 网关超时
     */
    public static final int GATEWAY_TIMEOUT = 504;

    /**
     * HTTP版本不受支持
     */
    public static final int HTTP_VERSION_NOT_SUPPORTED = 505;

    /**
     * 网络认证要求
     */
    public static final int NETWORK_AUTHENTICATION_REQUIRED = 511;

    /**
     * 配置错误
     */
    public static final int MISDIRECTED_REQUEST = 421;

    /**
     * 未处理的实体
     */
    public static final int UNPROCESSABLE_ENTITY = 422;

    /**
     * 锁定
     */
    public static final int LOCKED = 423;

    /**
     * 方法失败
     */
    public static final int FAILED_DEPENDENCY = 424;

    /**
     * 升级要求
     */
    public static final int UPGRADE_REQUIRED = 426;

    /**
     * 前端超时
     */
    public static final int PRECONDITION_REQUIRED = 428;

    /**
     * 太多请求
     */
    public static final int TOO_MANY_REQUESTS = 429;

    /**
     * 请求头字段过大
     */
    public static final int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;

    /**
     * 不可用的法律原因
     */
    public static final int UNAVAILABLE_FOR_LEGAL_REASONS = 451;
}