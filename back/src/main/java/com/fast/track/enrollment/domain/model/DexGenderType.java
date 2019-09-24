package com.irichment.enrollment.domain.model;

public enum DexGenderType {
    MALE, //0
    FEMALE; //1 ;

    public static DexGenderType get(int ordinal) { return values()[ordinal];}

}
