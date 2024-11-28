package com.tencent.wxcloudrun.common;

/**
 * 是或否枚举
 */
public enum YesOrNoEnum {
    YES(1),
    NO(0),
    ;

    /**
     * 获取V值
     *
     * @return V值
     */
    public int getV() {
        return v;
    }

    private int v;

    YesOrNoEnum(int v) {
        this.v = v;
    }
}
