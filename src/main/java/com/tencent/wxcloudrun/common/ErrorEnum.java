package com.tencent.wxcloudrun.common;

/**
 *
 */
public enum ErrorEnum {

    SUCCESS(200, "成功"),
    ERROR(1000, "失败"),
    USER_NOT_EXIST(10000, "用户不存在"),
    USER_EXIST(10001, "用户已存在"),
    USER_PASSWORD_ERROR(10002, "密码错误"),
    USER_LOGIN_ERROR(10003, "用户登录失败"),
    USER_PASSWORD_SAME(10004, "新旧密码一样"),
    USER_INVALID(10005, "用户名密码非法"),
    USER_PARAM_ERROR(10006, "用户名请求参数错误"),

    TOKEN_INVALID(20001, "请求验证失败"),
    PARAM_ERROR(20002, "参数错误"),

    Promo_Code_NO_EXIST(30001, "激活码不存在"),
    Promo_Code_Used(30002, "激活码已使用"),

    INVALID_SMS_CODE(40001, "验证码错误"),
    SMS_CODE_NOT_EXPIRED(40002, "验证码未过期"),
    SMS_CODE_EXPIRED(40003, "验证码已过期"),
    SMS_CODE_EXCEED_LIMIT(40004, "验证码发送次数超过限制"),

    BILLING_EXPIRED(50001, "订阅时间结束"),
    BILLING_ERROR(50002, "计费失败"),
    ;

    private int code;

    private String msg;

    ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
