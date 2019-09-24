package com.irichment.identity.api.vo;

/**
 * @author canang technologies
 */
public enum ActorType {
    STAFF, // 0
    GUARDIAN, //1
    INSTRUCTOR, //2
    THERAPIST; // 3

    public static ActorType get(int index) {
        return values()[index];
    }

}
