package com.irichment.enrollment.domain.model;

public enum DexStatusType {

    OPTIMAL, // 0;
    NORMAL, // 1
    PASS, // 2
    FAIL; // 3

    public static DexStatusType get(int ordinal) { return values()[ordinal]; }
}
