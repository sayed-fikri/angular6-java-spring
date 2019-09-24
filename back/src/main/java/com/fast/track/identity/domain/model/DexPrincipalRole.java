package com.irichment.identity.domain.model;

import com.irichment.core.domain.DexMetaObject;

/**
 * @author canang technologies
 */
public interface DexPrincipalRole extends DexMetaObject {

    DexPrincipal getPrincipal();

    void setPrincipal(DexPrincipal principal);

    DexRoleType getRole();

    void setRole(DexRoleType role);
}
