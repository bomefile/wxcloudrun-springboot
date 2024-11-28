package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.common.ErrorEnum;

public class ApiResponse<T> {

    private int code;
    private String msg;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null; // Data can be null if not used
    }

    public ApiResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // Getters and setters

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // Convenience methods for success and error
    public static ApiResponse success() {
        return new ApiResponse(200, "Success");
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse(200, "Success", data);
    }

    public static ApiResponse error(int code, String msg) {
        return new ApiResponse(code, msg);
    }

    public static ApiResponse error(ErrorEnum errorEnum) {
        return new ApiResponse(errorEnum.getCode(), errorEnum.getMsg());
    }

    public boolean isSuccess() {
        if (this.code == ErrorEnum.SUCCESS.getCode()) {
            return true;
        }
        return false;
    }

    public boolean isFailed() {
        if (this.code != ErrorEnum.SUCCESS.getCode()) {
            return true;
        }
        return false;
    }
}
