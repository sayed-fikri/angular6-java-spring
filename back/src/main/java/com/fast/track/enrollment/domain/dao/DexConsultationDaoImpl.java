package com.irichment.enrollment.domain.dao;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetaState;
import com.irichment.core.domain.DexMetadata;
import com.irichment.core.domain.GenericDaoSupport;
import com.irichment.enrollment.domain.model.*;
import com.irichment.helper.JpaResultHelper;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

@Repository("consultationDao")
public class DexConsultationDaoImpl extends GenericDaoSupport<Long, DexConsultation> implements DexConsultationDao {

    private static final Logger LOG = LoggerFactory.getLogger(DexConsultationDaoImpl.class);

    public DexConsultationDaoImpl() {
        super(DexConsultationImpl.class);
    }

    @Override
    public DexConsultation findByCode(String code) {
        Query query = entityManager.createQuery("select s from DexConsultation s where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexConsultation) query.getSingleResult();
    }

    @Override
    public DexConsultation findByChildAndTherapist(DexChild child, DexTherapist therapist) {
        Query query = entityManager.createQuery("select s from DexConsultation s where s.child = :child and  " +
                " s.therapist = :therapist and " +
                " s.metadata.state = :state");
        query.setParameter("child", child);
        query.setParameter("therapist", therapist);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexConsultation) query.getSingleResult();
    }

    @Override
    public DexComment findCommentByCode(String code) {
        Query query = entityManager.createQuery("select s from DexComment s where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexComment) query.getSingleResult();
    }

    @Override
    public List<DexConsultation> find(String filter, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexConsultation v where" +
                " upper(v.code) like upper(:filter)" +
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexConsultation>) query.getResultList();
    }

    @Override
    public List<DexConsultation> findByChild(DexChild child, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexConsultation s where " +
                "s.child =:child " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("child", child);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexConsultation>) query.getResultList();
    }

    @Override
    public List<DexConsultation> findByChild(DexChild child) {
        Query query = entityManager.createQuery("select s from DexConsultation s where " +
                "s.child =:child " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("child", child);
        if (query.getResultList().isEmpty())
            return null;
        else
            return (List<DexConsultation>) query.getResultList();
    }

    @Override
    public List<DexConsultation> findByTherapist(DexTherapist therapist, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexConsultation s where " +
                "s.therapist =:therapist " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("therapist", therapist);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexConsultation>) query.getResultList();
    }

    @Override
    public List<DexConsultation> findByTherapist(DexTherapist therapist) {
        Query query = entityManager.createQuery("select s from DexConsultation s where " +
                "s.therapist =:therapist " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("therapist", therapist);
        return (List<DexConsultation>) query.getResultList();
    }

    @Override
    public List<DexComment> findComments(DexConsultation consultation, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexComment v where" +
                " v.consultation =:consultation " +
                "and v.metadata.state = :metaState");
        query.setParameter("consultation", consultation);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexComment>) query.getResultList();
    }

    @Override
    public Integer count(String filter) {
        Query query = entityManager.createQuery("select count(v) from DexConsultation v where" +
                " upper(v.code) like upper(:filter) " +
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countByChild(DexChild child) {
        Query query = entityManager.createQuery("select count(s) from DexConsultation s where " +
                "s.child =:child " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("child", child);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countByTherapist(DexTherapist therapist) {
        Query query = entityManager.createQuery("select count(s) from DexConsultation s where " +
                "s.therapist =:therapist " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("therapist", therapist);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countComment(DexConsultation consultation) {
        Query query = entityManager.createQuery("select count(s) from DexComment s where " +
                "s.consultation =:consultation " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("consultation", consultation);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public boolean isExists(String code) {
        Query query = entityManager.createQuery("select count(s) from DexConsultation s where " +
                "s.code = :code " +
                "and s.metadata.state = :state ");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return 0 < ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public void addComment(DexConsultation consultation, DexComment comment, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(comment, "Comment cannot be null");
        Assert.notNull(consultation, "Consultation cannot be null");
        comment.setConsultation(consultation);
        prepareMetadata(comment, user, true);
        entityManager.persist(comment);
        entityManager.flush();
    }

    @Override
    public void updateComment(DexComment comment, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(comment, "Comment cannot be null");
        prepareMetadata(comment, user, true);
        entityManager.merge(comment);
        entityManager.flush();
    }

    @Override
    public void deleteComment(DexComment comment, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(comment, "Comment cannot be null");
        prepareMetadata(comment, user, false);
        entityManager.merge(comment);
        entityManager.flush();

    }

    private void prepareMetadata(Object i, DexUser user, boolean active) {
        if (i instanceof DexMetaObject) {
            DexMetadata metadata = null;
            if (((DexMetaObject) i).getMetadata() != null)
                metadata = ((DexMetaObject) i).getMetadata();
            else
                metadata = new DexMetadata();
            metadata.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            metadata.setCreatorId(user.getId());
            metadata.setState(active ? DexMetaState.ACTIVE : DexMetaState.INACTIVE);
            ((DexMetaObject) i).setMetadata(metadata);
        }
    }
}
