package com.tencent.wxcloudrun.common;

public class BusinessException extends RuntimeException {

    private int code;
    private String message;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    // 可以根据业务需求定义更多的属性和构造方法

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static BusinessException buildException(ErrorEnum errorEnum) {
        return new BusinessException(errorEnum.getCode(), errorEnum.getMsg());
    }

}
