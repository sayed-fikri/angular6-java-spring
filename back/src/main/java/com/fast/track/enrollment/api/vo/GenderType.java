package com.irichment.enrollment.api.vo;

public enum GenderType {

    MALE, // 0
    FEMALE ; // 1;

    public static GenderType get(int ordinal) {
        return values()[ordinal];
    }
}
