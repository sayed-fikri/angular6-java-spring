package com.irichment.identity.domain.model;

import com.irichment.identity.api.vo.RoleType;

/**
 * @author canang technologies
 */
public enum DexRoleType {
    ROLE_ADMINISTRATOR, // 0
    ROLE_STAFF,         // 1
    ROLE_GUARDIAN,      // 2
    ROLE_THERAPIST;     // 3

    public static DexRoleType get(int index) {
        return values()[index];
    }
}
