package com.irichment.identity.domain.model;



/**
 * @author  canang technologies
 */
public interface DexGuardian extends DexActor {

    Boolean isOnboarded();

    void setOnboarded(Boolean onboarded);

}
