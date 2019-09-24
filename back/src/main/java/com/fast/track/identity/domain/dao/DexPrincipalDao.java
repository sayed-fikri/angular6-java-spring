package com.irichment.identity.domain.dao;


import com.irichment.core.domain.GenericDao;
import com.irichment.identity.domain.model.*;
import com.irichment.identity.domain.model.DexPrincipalRole;
import com.irichment.identity.domain.model.DexUser;

import java.util.List;

/**
 * @author canang technologies
 * @since 1/30/14
 */
public interface DexPrincipalDao extends GenericDao<Long, DexPrincipal> {

    // ====================================================================================================
    // HELPERS
    // ====================================================================================================

    // ====================================================================================================
    // FINDER
    // ====================================================================================================

    DexPrincipal findByName(String name);

    List<DexPrincipal> findAllPrincipals();

    List<DexPrincipal> find(String filter);

    List<DexPrincipal> find(String filter, DexPrincipalType type);

    List<DexPrincipal> find(String filter, Integer offset, Integer limit);

    void addRole(DexPrincipal principal, DexPrincipalRole principalRole, DexUser user);

    void deleteRole(DexPrincipal principal, DexPrincipalRole principalRole, DexUser user);

    Integer count(String filter);

    // ====================================================================================================
    // CRUD
    // ====================================================================================================

}
