package com.irichment.enrollment.domain.model;

import com.irichment.enrollment.api.vo.ConcernType;

/**
 * @author canang technologies
 */
public enum DexConcernType {
    SPEECH,
    HEARING,
    BEHAVIOR;

    public static DexConcernType get(int ordinal){
        return values()[ordinal];
    }
}
