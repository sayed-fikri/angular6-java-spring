package com.irichment.identity.domain.model;


import com.irichment.core.domain.DexMetaObject;

/**
 * @author canang technologies
 */
public interface DexGroupMember extends DexMetaObject {

    DexGroup getGroup();

    void setGroup(DexGroup group);

    DexPrincipal getPrincipal();

    void setPrincipal(DexPrincipal principal);
}
