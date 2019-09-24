package com.irichment.enrollment.domain.dao;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetaState;
import com.irichment.core.domain.DexMetadata;
import com.irichment.core.domain.GenericDaoSupport;
import com.irichment.enrollment.domain.model.DexCoupon;
import com.irichment.enrollment.domain.model.DexCouponImpl;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

@Repository("couponDao")
public class DexCouponDaoImpl extends GenericDaoSupport<Long, DexCoupon> implements DexCouponDao {

    private static final Logger LOG = LoggerFactory.getLogger(DexCouponDaoImpl.class);

    public DexCouponDaoImpl() {
        super(DexCouponImpl.class);
    }

    @Override
    public DexCoupon findByCode(String code) {
        Query query = entityManager.createQuery("select s from DexCoupon s where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexCoupon) query.getSingleResult();
    }

    @Override
    public DexCoupon findAvailableCouponByCode(String code) {
        Query query = entityManager.createQuery("select s from DexCoupon s where s.code = :code and  " +
                " s.redeemed <> true " +
                " and s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexCoupon) query.getSingleResult();
    }

    @Override
    public List<DexCoupon> findAllCoupons() {
        Query q = entityManager.createQuery("select e from DexCoupon e ");
        return q.getResultList();

    }

    @Override
    public List<DexCoupon> findCoupons(String filter, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexCoupon v where" +
                " upper(v.code) like upper(:filter)" +
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexCoupon>) query.getResultList();
    }


    @Override
    public Integer count(String filter) {
        Query query = entityManager.createQuery("select count(v) from DexCoupon v where" +
                " upper(v.code) like upper(:filter) " +
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }
}
