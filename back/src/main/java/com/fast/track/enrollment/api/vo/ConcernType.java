package com.irichment.enrollment.api.vo;

/**
 * @author canang technologies
 */
public enum ConcernType {
    SPEECH,
    HEARING,
    BEHAVIOR;

    public static ConcernType get(int ordinal){
        return values()[ordinal];
    }

}
