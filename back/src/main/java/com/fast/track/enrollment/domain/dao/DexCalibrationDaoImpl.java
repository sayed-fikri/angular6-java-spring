package com.irichment.enrollment.domain.dao;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetaState;
import com.irichment.core.domain.DexMetadata;
import com.irichment.core.domain.GenericDaoSupport;
import com.irichment.enrollment.domain.model.*;
import com.irichment.identity.domain.model.DexUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

@Repository("calibrationDao")
public class DexCalibrationDaoImpl extends GenericDaoSupport<Long, DexCalibration> implements DexCalibrationDao {

    private static final Logger LOG = LoggerFactory.getLogger(DexCalibrationDaoImpl.class);


    public DexCalibrationDaoImpl() {
        super(DexCalibrationDao.class);
    }

    @Override
    public DexCalibrationSectionResponse findSectionResponseById(Long id) {
        Query query = entityManager.createQuery("select s from DexCalibrationSectionResponse s " +
                "where s.id = :id and  " +
                " s.metadata.state = :state");
        query.setParameter("id", id);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexCalibrationSectionResponse) query.getSingleResult();
    }

    @Override
    public DexCalibrationQuestionResponse findQuestionResponseById(Long id) {
        Query query = entityManager.createQuery("select s from DexCalibrationQuestionResponse s " +
                "where s.id = :id and  " +
                " s.metadata.state = :state");
        query.setParameter("id", id);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexCalibrationQuestionResponse) query.getSingleResult();
    }

    @Override
    public DexCalibration findCalibrationByCode(String code) {
        Query query = entityManager.createQuery("select s from DexCalibration s " +
                "where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexCalibration) query.getSingleResult();
    }

    @Override
    public DexCalibration findCalibrationByChild(DexChild child) {
        Query query = entityManager.createQuery("select s from DexCalibration s where " +
                "s.child =:child " +
                "and s.metadata.state = :state ");
        query.setParameter("child", child);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexCalibration) query.getSingleResult();
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
    public DexCalibrationSectionResponse findSectionResponseByCode(String code) {
        Query query = entityManager.createQuery("select s from DexCalibrationSectionResponse s " +
                "where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexCalibrationSectionResponse) query.getSingleResult();
    }

    @Override
    public List<DexCalibrationSectionResponse> findSectionResponseByCalibration(DexCalibration calibration) {
        Query query = entityManager.createQuery("select s from DexCalibrationSectionResponse s " +
                "where s.calibration =:calibration " +
                "and s.metadata.state = :state");
        query.setParameter("calibration", calibration);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((List<DexCalibrationSectionResponse>) query.getResultList());
    }


    @Override
    public DexCalibrationQuestionResponse findQuestionResponseByCode(String code) {
        Query query = entityManager.createQuery("select s from DexCalibrationQuestionResponse s " +
                "where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexCalibrationQuestionResponse) query.getSingleResult();
    }

    @Override
    public DexCalibrationQuestionResponse findQuestionResponseBySectionResponse(DexCalibrationSectionResponse sectionResponse) {
        Query query = entityManager.createQuery("select s from DexCalibrationQuestionResponse s " +
                "where s.sectionResponse =:sectionResponse " +
                "and s.metadata.state = :state");
        query.setParameter("sectionResponse", sectionResponse);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexCalibrationQuestionResponse) query.getSingleResult();
    }

    @Override
    public List<DexCalibration> findCalibrations(String filter, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexCalibration s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return ((List<DexCalibration>) query.getResultList());
    }

    @Override
    public List<DexCalibrationSectionResponse> findSectionResponses(DexCalibration calibration,
                                                                   Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexCalibrationSectionResponse s " +
                "where s.calibration = :calibration and  " +
                " s.metadata.state = :state");
        query.setParameter("calibration", calibration);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return ((List<DexCalibrationSectionResponse>) query.getResultList());
    }

    @Override
    public List<DexCalibrationQuestionResponse> findQuestionResponses(DexCalibrationSectionResponse sectionResponse) {
        Query query = entityManager.createQuery("select s from DexCalibrationQuestionResponse s " +
                "where s.sectionResponse = :sectionResponse and  " +
                " s.metadata.state = :state");
        query.setParameter("sectionResponse", sectionResponse);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((List<DexCalibrationQuestionResponse>) query.getResultList());
    }

    @Override
    public List<DexCalibrationQuestionResponse> findQuestionResponses(DexCalibrationSectionResponse sectionResponse,
                                                                     Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexCalibrationQuestionResponse s " +
                "where s.sectionResponse = :sectionResponse and  " +
                " s.metadata.state = :state");
        query.setParameter("sectionResponse", sectionResponse);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return ((List<DexCalibrationQuestionResponse>) query.getResultList());
    }


    @Override
    public Integer count(String filter) {
        Query query = entityManager.createQuery("select count(s) from DexCalibration s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countSectionResponse(DexCalibration calibration) {
        Query query = entityManager.createQuery("select count(s) from DexCalibrationSectionResponse s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + calibration + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countQuestionResponse(DexCalibrationSectionResponse sectionResponse) {
        Query query = entityManager.createQuery("select count(s) from DexCalibrationQuestionResponse s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + sectionResponse + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public boolean isExists(String code) {
        Query query = entityManager.createQuery("select count(s) from DexCalibration s where " +
                "s.code = :code " +
                "and s.metadata.state = :state ");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return 0 < ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public void addCalibration(DexCalibration calibration, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(calibration, "Calibration cannot be null");
        prepareMetadata(calibration, user, true);
        entityManager.persist(calibration);
        entityManager.flush();
    }

    @Override
    public void addSectionResponse(DexCalibration calibration,
                                   DexCalibrationSectionResponse sectionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(calibration, "Calibration cannot be null");
        Assert.notNull(sectionResponse, "Feedback cannot be null");
        sectionResponse.setCalibration(calibration);
        prepareMetadata(sectionResponse, user, true);
        entityManager.persist(sectionResponse);
        entityManager.flush();
    }

    @Override
    public void addQuestionResponse(DexCalibrationSectionResponse sectionResponse,
                                    DexCalibrationQuestionResponse questionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(sectionResponse, "Calibration cannot be null");
        Assert.notNull(questionResponse, "Feedback cannot be null");
        questionResponse.setSectionResponse(sectionResponse);
        prepareMetadata(questionResponse, user, true);
        entityManager.persist(questionResponse);
        entityManager.flush();
    }

    @Override
    public void updateCalibration(DexCalibration calibration, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(calibration, "Calibration cannot be null");
        prepareMetadata(calibration, user, true);
        entityManager.merge(calibration);
        entityManager.flush();
    }

    @Override
    public void updateSectionResponse(DexCalibration calibration,
                                      DexCalibrationSectionResponse sectionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(calibration, "Calibration cannot be null");
        Assert.notNull(sectionResponse, "Section Feedback cannot be null");
        prepareMetadata(sectionResponse, user, true);
        sectionResponse.setCalibration(calibration);
        entityManager.merge(sectionResponse);
        entityManager.flush();
    }

    @Override
    public void updateQuestionResponse(DexCalibrationQuestionResponse questionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(questionResponse, "Question Feedback cannot be null");
        prepareMetadata(questionResponse,user, true);
        entityManager.merge(questionResponse);
        entityManager.flush();
    }

    @Override
    public void deleteCalibration(DexCalibration calibration, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(calibration, "Calibration cannot be null");
        prepareMetadata(calibration, user, true);
        entityManager.merge(calibration);
        entityManager.flush();
    }

    @Override
    public void deleteSectionResponse(DexCalibration calibration,
                                     DexCalibrationSectionResponse sectionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(calibration, "Calibration cannot be null");
        Assert.notNull(sectionResponse, "Feedback cannot be null");
        prepareMetadata(sectionResponse, user, true);
        sectionResponse.setCalibration(calibration);
        entityManager.merge(sectionResponse);
        entityManager.flush();
    }

    @Override
    public void deleteQuestionResponse(DexCalibrationSectionResponse sectionResponse,
                                     DexCalibrationQuestionResponse questionResponse, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(sectionResponse, "Calibration cannot be null");
        Assert.notNull(questionResponse, "Feedback cannot be null");
        prepareMetadata(questionResponse, user, true);
        questionResponse.setSectionResponse(sectionResponse);
        entityManager.merge(questionResponse);
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
