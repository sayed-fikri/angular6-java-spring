package com.irichment.enrollment.domain.model;

public enum DexAnswerType {

    NO, // 0;
    JUST_APPEARED, // 1
    YES; // 2

    public static DexAnswerType get(int ordinal) { return values()[ordinal]; }
}
