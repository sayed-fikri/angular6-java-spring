package com.irichment.identity.domain.dao;


import com.irichment.core.domain.DexMetaState;
import com.irichment.core.domain.GenericDaoSupport;
import com.irichment.identity.domain.model.DexActorType;
import com.irichment.identity.domain.model.DexStaff;
import com.irichment.identity.domain.model.DexStaffImpl;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.Query;

@Repository("staffDao")
public class DexStaffDaoImpl extends GenericDaoSupport<Long, DexStaff> implements DexStaffDao {

    public DexStaffDaoImpl() {
        super(DexStaffImpl.class);
    }

    @Override
    public DexStaff findByCode(String code) {
        Query query = entityManager.createQuery("select u from DexStaff u where " +
                "u.code = :code ");
        query.setParameter("code", code);
        return (DexStaff) query.getSingleResult();
    }

    @Override
    public DexStaff findByIdentityNo(String identityNo) {
        Query query = entityManager.createQuery("select u from DexStaff u where " +
                "u.identityNo = :identityNo ");
        query.setParameter("identityNo", identityNo);
        return (DexStaff) query.getSingleResult();
    }

    @Override
    public DexStaff findByEmail(String email) {
        Query query = entityManager.createQuery("select u from DexStaff u where " +
                "u.email = :identityNo ");
        query.setParameter("identityNo", email);
        return (DexStaff) query.getSingleResult();
    }

    @Override
    public DexStaff findByActorType(DexActorType actorType) {
        Query query = entityManager.createQuery("select u from DexStaff u where " +
                "u.actorType = :actorType ");
        query.setParameter("actorType", actorType);
        return (DexStaff) query.getSingleResult();
    }

    @Override
    public boolean isEmailExists(String email) {
        Query query = entityManager.createQuery("select count(u) from DexStaff u where " +
                "upper(u.email) = upper(:email) ");
        query.setParameter("email", email);
        return 0 < ((Long) query.getSingleResult()).intValue();
    }


    @Override
    public List<DexStaff> findStaffs(String filter, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexStaff v where"+
                "(upper(v.name) like upper(:filter)" +
                "or upper(v.code) like upper(:filter))"+
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexStaff>) query.getResultList();
    }

    @Override
    public List<DexStaff> findStaffs(Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexStaff v where"+
                " v.metadata.state = :metaState");
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexStaff>) query.getResultList();
    }

    @Override
    public Integer count(String filter) {
        Query query = entityManager.createQuery("select count(v) from DexStaff v where"+
                "(upper(v.name) like upper(:filter)" +
                "or upper(v.code) like upper(:filter))"+
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public DexStaff findStaffByCode(String code) {
        Query query = entityManager.createQuery("select u from DexStaff u where " +
                "u.code = :code "+
                "and u.metadata.state = :metaState");
        query.setParameter("code", code);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        return (DexStaff) query.getSingleResult();
    }


}
