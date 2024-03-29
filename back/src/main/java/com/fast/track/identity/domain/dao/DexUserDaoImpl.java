package com.irichment.identity.domain.dao;

import com.irichment.core.domain.GenericDaoSupport;
import com.irichment.helper.JpaResultHelper;
import com.irichment.identity.domain.model.DexGroup;
import com.irichment.identity.domain.model.DexUser;
import com.irichment.identity.domain.model.DexUserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author canang technologies
 */
@Repository("userDao")
public class DexUserDaoImpl extends GenericDaoSupport<Long, DexUser> implements DexUserDao {

    private static final Logger LOG = LoggerFactory.getLogger(DexUserDaoImpl.class);

    public DexUserDaoImpl() {
        super(DexUserImpl.class);
    }

    // =============================================================================
    // FINDER METHODS
    // =============================================================================

    @Override
    public List<DexGroup> findGroups(DexUser user) {
        Query query = entityManager.createQuery("select r from DexGroup r inner join r.members m where m.id = :id");
        query.setParameter("id", user.getId());
        return (List<DexGroup>) query.getResultList();
    }

    @Override
    public boolean isExists(String username) {
        Query query = entityManager.createQuery("select count(u) from DexUser u where " +
                "upper(u.name) = upper(:username) ");
        query.setParameter("username", username);
        return 0 < ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public DexUser findByEmail(String email) {
        LOG.debug("email = " + email);
        Query query = entityManager.createQuery("select u from DexUser u where u.email = :email ");
        query.setParameter("email", email);
        return JpaResultHelper.getSingleResultOrNull(query);
    }

    @Override
    public DexUser findByUsername(String username) {
        LOG.debug("username = " + username);
        Query query = entityManager.createQuery("select u from DexUser u where u.name = :username ");
        query.setParameter("username", username);
        return JpaResultHelper.getSingleResultOrNull(query);
    }

    @Override
    public List<DexUser> find(String filter, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexUser s where (" +
                "upper(s.name) like upper(:filter) " +
                "or upper(s.realName) like upper(:filter)) " +
                "order by s.realName, s.name");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexUser>) query.getResultList();
    }

    @Override
    public Integer count(String filter) {
        Query query = entityManager.createQuery("select count(s) from DexUser s where " +
                "upper(s.name) like upper(:filter) " +
                "or upper(s.realName) like upper(:filter)");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        return ((Long) query.getSingleResult()).intValue();
    }
}
