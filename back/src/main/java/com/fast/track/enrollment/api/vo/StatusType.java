package com.irichment.enrollment.api.vo;

public enum StatusType {

    OPTIMAL, // 0
    NORMAL, // 1;
    PASS, // 2;
    FAIL; // 3;

    public static StatusType get(int ordinal) {
        return values()[ordinal];
    }
}
