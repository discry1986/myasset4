package com.discry.myasset.model.sys;

/**
 * 状态枚举类
 * 已删除-0，正常-1，禁用-2
 */
public enum StatusEnum {
    DELETED("已删除"), NORMAL("正常"), DISABLED("已禁用");

    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
