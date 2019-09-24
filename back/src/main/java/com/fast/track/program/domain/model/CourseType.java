package com.fast.track.program.domain.model;

public enum CourseType {

    ACADEMIC,
    CO_CURRICULUM;
    public static CourseType get(int index) {
        return values()[index];
    }
}
