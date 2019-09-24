package com.irichment.identity.api.vo;


import com.irichment.core.api.MetaObject;

/**
 */
public class GroupMember extends MetaObject {
    private Principal principal;

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}
