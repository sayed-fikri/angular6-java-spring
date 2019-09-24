package com.irichment.enrollment.api.vo;

public enum AnswerType {
    TIDAK,// 0
    BARU_MUNCUL, // 1
    YA ;// 2;

    public static AnswerType get(int ordinal){
        return values()[ordinal];
    }
}
