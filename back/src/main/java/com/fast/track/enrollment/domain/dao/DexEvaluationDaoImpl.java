package com.irichment.enrollment.domain.dao;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetaState;
import com.irichment.core.domain.DexMetadata;
import com.irichment.core.domain.GenericDaoSupport;
import com.irichment.enrollment.domain.model.*;
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

@Repository("evaluationDao")
public class DexEvaluationDaoImpl extends GenericDaoSupport<Long, DexEvaluation> implements DexEvaluationDao {

    private static final Logger LOG = LoggerFactory.getLogger(DexEvaluationDaoImpl.class);
    public DexEvaluationDaoImpl() {
        super(DexEvaluationDao.class);
    }

    @Override
    public DexEvaluationSectionResponse findSectionResponseById(Long id) {
        Query query = entityManager.createQuery("select s from DexEvaluationSectionResponse s " +
                "where s.id = :id and  " +
                " s.metadata.state = :state");
        query.setParameter("id", id);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationSectionResponse) query.getSingleResult();
    }

    @Override
    public DexEvaluationQuestionResponse findQuestionResponseById(Long id) {
        Query query = entityManager.createQuery("select s from DexEvaluationQuestionResponse s " +
                "where s.id = :id and  " +
                " s.metadata.state = :state");
        query.setParameter("id", id);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationQuestionResponse) query.getSingleResult();
    }

    @Override
    public DexEvaluation findEvaluationByCode(String code) {
        Query query = entityManager.createQuery("select s from DexEvaluation s " +
                "where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluation) query.getSingleResult();
    }

    @Override
    public DexEvaluation findEvaluationByChild(DexChild child) {
        Query query = entityManager.createQuery("select s from DexEvaluation s where " +
                "s.child =:child " +
                "and s.metadata.state = :state ");
        query.setParameter("child", child);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluation) query.getSingleResult();
    }


    @Override
    public DexEvaluationStatus findEvaluationStatusBySchemaAndTotalScore(DexEvaluationSchema schema, Integer totalScore) {
        Query query = entityManager.createQuery("select s from DexEvaluationStatus s " +
                "where s.schema =:schema and" +
                " s.min <= :totalScore " + " and s.max >= :totalScore "+
                "and s.metadata.state = :state");
        query.setParameter("schema", schema);
        query.setParameter("totalScore", totalScore);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationStatus) query.getSingleResult();
    }

    @Override
    public DexEvaluationSectionResponse findSectionResponseByCode(String code) {
        Query query = entityManager.createQuery("select s from DexEvaluationSectionResponse s " +
                "where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationSectionResponse) query.getSingleResult();
    }

    @Override
    public List<DexEvaluationSectionResponse> findSectionResponseByEvaluation(DexEvaluation evaluation) {
        Query query = entityManager.createQuery("select s from DexEvaluationSectionResponse s " +
                "where s.evaluation =:evaluation " +
                "and s.metadata.state = :state");
        query.setParameter("evaluation", evaluation);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((List<DexEvaluationSectionResponse>) query.getResultList());
    }


    @Override
    public DexEvaluationQuestionResponse findQuestionResponseByCode(String code) {
        Query query = entityManager.createQuery("select s from DexEvaluationQuestionResponse s " +
                "where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationQuestionResponse) query.getSingleResult();
    }

    @Override
    public DexEvaluationQuestionResponse findQuestionResponseBySectionResponse(DexEvaluationSectionResponse sectionResponse) {
        Query query = entityManager.createQuery("select s from DexEvaluationQuestionResponse s " +
                "where s.sectionResponse =:sectionResponse " +
                "and s.metadata.state = :state");
        query.setParameter("sectionResponse", sectionResponse);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationQuestionResponse) query.getSingleResult();
    }

    @Override
    public List<DexEvaluation> findEvaluations(String filter, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexEvaluation s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return ((List<DexEvaluation>) query.getResultList());
    }

    @Override
    public List<DexEvaluation> findEvaluationsByChild(DexChild child) {
        Query query = entityManager.createQuery("select s from DexEvaluation s where " +
                " s.child =:child" +
                " and s.metadata.state = :state ");
        query.setParameter("child", child);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (List<DexEvaluation>) query.getResultList();
    }

    @Override
    public List<DexEvaluation> findEvaluationsByGuardian(DexGuardian guardian, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexEvaluation s where " +
                " s.guardian =:guardian" +
                " and s.metadata.state = :state ");
        query.setParameter("guardian", guardian);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexEvaluation>) query.getResultList();
    }

    @Override
    public List<DexEvaluationSectionResponse> findSectionResponses(DexEvaluation evaluation,
                                                                   Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexEvaluationSectionResponse s " +
                "where s.evaluation = :evaluation and  " +
                " s.metadata.state = :state");
        query.setParameter("evaluation", evaluation);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return ((List<DexEvaluationSectionResponse>) query.getResultList());
    }

    @Override
    public List<DexEvaluationQuestionResponse> findQuestionResponses(DexEvaluationSectionResponse sectionResponse) {
        Query query = entityManager.createQuery("select s from DexEvaluationQuestionResponse s " +
                "where s.sectionResponse = :sectionResponse and  " +
                " s.metadata.state = :state");
        query.setParameter("sectionResponse", sectionResponse);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((List<DexEvaluationQuestionResponse>) query.getResultList());
    }

    @Override
    public List<DexEvaluationQuestionResponse> findQuestionResponses(DexEvaluationSectionResponse sectionResponse,
                                                                     Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexEvaluationQuestionResponse s " +
                "where s.sectionResponse = :sectionResponse and  " +
                " s.metadata.state = :state");
        query.setParameter("sectionResponse", sectionResponse);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return ((List<DexEvaluationQuestionResponse>) query.getResultList());
    }


    @Override
    public Integer count(String filter) {
        Query query = entityManager.createQuery("select count(s) from DexEvaluation s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer count(DexGuardian guardian) {
        Query query = entityManager.createQuery("select count(s) from DexEvaluation s where " +
                "s.guardian = :guardian " +
                "and s.metadata.state = :state ");
        query.setParameter("guardian", guardian);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countSectionResponse(DexEvaluation evaluation) {
        Query query = entityManager.createQuery("select count(s) from DexEvaluationSectionResponse s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + evaluation + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countQuestionResponse(DexEvaluationSectionResponse sectionResponse) {
        Query query = entityManager.createQuery("select count(s) from DexEvaluationQuestionResponse s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + sectionResponse + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public boolean isExists(String code) {
        Query query = entityManager.createQuery("select count(s) from DexEvaluation s where " +
                "s.code = :code " +
                "and s.metadata.state = :state ");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return 0 < ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public void addEvaluation(DexEvaluation evaluation, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(evaluation, "Evaluation cannot be null");
        prepareMetadata(evaluation, user, true);
        entityManager.persist(evaluation);
        entityManager.flush();
    }

    @Override
    public void addSectionResponse(DexEvaluation evaluation,
                                   DexEvaluationSectionResponse sectionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(evaluation, "Evaluation cannot be null");
        Assert.notNull(sectionResponse, "Feedback cannot be null");
        sectionResponse.setEvaluation(evaluation);
        prepareMetadata(sectionResponse, user, true);
        entityManager.persist(sectionResponse);
        entityManager.flush();
    }

    @Override
    public void addQuestionResponse(DexEvaluationSectionResponse sectionResponse,
                                    DexEvaluationQuestionResponse questionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(sectionResponse, "Evaluation cannot be null");
        Assert.notNull(questionResponse, "Feedback cannot be null");
        questionResponse.setSectionResponse(sectionResponse);
        prepareMetadata(questionResponse, user, true);
        entityManager.persist(questionResponse);
        entityManager.flush();
    }

    @Override
    public void updateEvaluation(DexEvaluation evaluation, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(evaluation, "Evaluation cannot be null");
        prepareMetadata(evaluation, user, true);
        entityManager.merge(evaluation);
        entityManager.flush();
    }

    @Override
    public void updateSectionResponse(DexEvaluation evaluation,
                                      DexEvaluationSectionResponse sectionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(evaluation, "Evaluation cannot be null");
        Assert.notNull(sectionResponse, "Section Feedback cannot be null");
        prepareMetadata(sectionResponse, user, true);
        sectionResponse.setEvaluation(evaluation);
        entityManager.merge(sectionResponse);
        entityManager.flush();
    }

    @Override
    public void updateSectionResponse(DexCalibration calibration, DexEvaluationSectionResponse sectionResponse, DexUser user) {

    }

    @Override
    public void updateQuestionResponse(DexEvaluationQuestionResponse questionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(questionResponse, "Question Feedback cannot be null");
        prepareMetadata(questionResponse,user, true);
        entityManager.merge(questionResponse);
        entityManager.flush();
    }

    @Override
    public void deleteEvaluation(DexEvaluation evaluation, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(evaluation, "Evaluation cannot be null");
        prepareMetadata(evaluation, user, true);
        entityManager.merge(evaluation);
        entityManager.flush();
    }

    @Override
    public void deleteSectionResponse(DexEvaluation evaluation,
                                     DexEvaluationSectionResponse sectionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(evaluation, "Evaluation cannot be null");
        Assert.notNull(sectionResponse, "Feedback cannot be null");
        prepareMetadata(sectionResponse, user, true);
        sectionResponse.setEvaluation(evaluation);
        entityManager.merge(sectionResponse);
        entityManager.flush();
    }

    @Override
    public void deleteQuestionResponse(DexEvaluationSectionResponse sectionResponse,
                                     DexEvaluationQuestionResponse questionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(sectionResponse, "Evaluation cannot be null");
        Assert.notNull(questionResponse, "Feedback cannot be null");
        prepareMetadata(questionResponse, user, true);
        questionResponse.setSectionResponse(sectionResponse);
        entityManager.merge(questionResponse);
        entityManager.flush();
    }

    @Override
    public void prepareMetadata(Object i, DexUser user, boolean active) {
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
