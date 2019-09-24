package com.irichment.identity.domain.dao;


import com.irichment.core.domain.DexMetaState;
import com.irichment.core.domain.GenericDaoSupport;
import com.irichment.identity.domain.model.DexActorType;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexGuardianImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository("guardianDao")
public class DexGuardianDaoImpl extends GenericDaoSupport<Long, DexGuardian> implements DexGuardianDao {

    public DexGuardianDaoImpl() {
        super(DexGuardianImpl.class);
    }

    @Override
    public DexGuardian findByCode(String code) {
        Query query = entityManager.createQuery("select u from DexGuardian u where " +
                "u.code = :code ");
        query.setParameter("code", code);
        return (DexGuardian) query.getSingleResult();
    }

    @Override
    public DexGuardian findByIdentityNo(String identityNo) {
        Query query = entityManager.createQuery("select u from DexGuardian u where " +
                "u.identityNo = :identityNo ");
        query.setParameter("identityNo", identityNo);
        return (DexGuardian) query.getSingleResult();
    }

    @Override
    public DexGuardian findByEmail(String email) {
        Query query = entityManager.createQuery("select u from DexGuardian u where " +
                "u.email = :identityNo ");
        query.setParameter("identityNo", email);
        return (DexGuardian) query.getSingleResult();
    }

    @Override
    public boolean isEmailExists(String email) {
        Query query = entityManager.createQuery("select count(u) from DexGuardian u where " +
                "upper(u.email) = upper(:email) ");
        query.setParameter("email", email);
        return 0 < ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public List<DexGuardian> findGuardians(String filter, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexGuardian v where"+
                "(upper(v.name) like upper(:filter)" +
                "or upper(v.code) like upper(:filter))"+
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexGuardian>) query.getResultList();
    }

    @Override
    public List<DexGuardian> findGuardians(Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexGuardian v where"+
                " v.metadata.state = :metaState");
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexGuardian>) query.getResultList();
    }

    @Override
    public Integer count(String filter) {
        Query query = entityManager.createQuery("select count(v) from DexGuardian v where"+
                "(upper(v.name) like upper(:filter)" +
                "or upper(v.code) like upper(:filter))"+
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public DexGuardian findGuardianByCode(String code) {
        Query query = entityManager.createQuery("select u from DexGuardian u where " +
                "u.code = :code "+
                "and u.metadata.state = :metaState");
        query.setParameter("code", code);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        return (DexGuardian) query.getSingleResult();
    }


}
