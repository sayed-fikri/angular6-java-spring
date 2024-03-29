package com.irichment.identity.domain.dao;

import com.irichment.core.domain.GenericDao;
import com.irichment.identity.domain.model.DexGroup;
import com.irichment.identity.domain.model.DexUser;

import java.util.List;

/**
 * @author canang technologies
 */
public interface DexUserDao extends GenericDao<Long, DexUser> {

    DexUser findByEmail(String email);

    DexUser findByUsername(String username);

    List<DexUser> find(String filter, Integer offset, Integer limit);

    List<DexGroup> findGroups(DexUser user);

    Integer count(String filter);

    boolean isExists(String username);
}
