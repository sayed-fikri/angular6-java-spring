package com.irichment.identity.api.vo;

/**
 * @author canang technologies
 */
public enum RoleType {
    ROLE_ADMINISTRATOR, // 0
    ROLE_STAFF,         // 1
    ROLE_GUARDIAN,      // 2
    ROLE_THERAPIST;      // 3

    public static RoleType get(int index) {
        return values()[index];
    }


}
