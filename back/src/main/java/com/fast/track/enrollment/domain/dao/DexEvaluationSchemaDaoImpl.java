package com.irichment.enrollment.domain.dao;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetaState;
import com.irichment.core.domain.DexMetadata;
import com.irichment.core.domain.GenericDaoSupport;
import com.irichment.enrollment.domain.model.DexEvaluationAnswer;
import com.irichment.enrollment.domain.model.DexEvaluationQuestion;
import com.irichment.enrollment.domain.model.DexEvaluationSchema;
import com.irichment.enrollment.domain.model.DexEvaluationSection;
import com.irichment.identity.domain.model.DexUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.Query;

import java.sql.Timestamp;
import java.util.List;

@Repository("evaluationSchemaDao")
public class DexEvaluationSchemaDaoImpl extends GenericDaoSupport<Long, DexEvaluationSchema> implements DexEvaluationSchemaDao {

    private static final Logger LOG = LoggerFactory.getLogger(DexEvaluationSchemaDaoImpl.class);

    public DexEvaluationSchemaDaoImpl() {
        super(DexEvaluationSchemaDao.class);
    }

    @Override
    public DexEvaluationSection findSectionById(Long id) {
        Query query = entityManager.createQuery("select s from DexEvaluationSchema s where s.id = :id and  " +
                " s.metadata.state = :state");
        query.setParameter("id", id);
        return (DexEvaluationSection) query.getSingleResult();
    }

    @Override
    public DexEvaluationQuestion findQuestionById(Long id) {
        Query query = entityManager.createQuery("select s from DexEvaluationSchema s where s.id = :id and  " +
                " s.metadata.state = :state");
        query.setParameter("id", id);
        return (DexEvaluationQuestion) query.getSingleResult();
    }

    @Override
    public DexEvaluationAnswer findAnswerById(Long id) {
        Query query = entityManager.createQuery("select s from DexEvaluationSchema s where s.id = :id and  " +
                " s.metadata.state = :state");
        query.setParameter("id", id);
        return (DexEvaluationAnswer) query.getSingleResult();
    }

    @Override
    public DexEvaluationSchema findByCode(String code) {
        Query query = entityManager.createQuery("select s from DexEvaluationSchema s where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationSchema) query.getSingleResult();
    }

    @Override
    public DexEvaluationSchema findSchemaByAge(Integer age) {
        Query query = entityManager.createQuery("select s from DexEvaluationSchema s " +
                "where s.minAge <= :age " + " and s.maxAge >= :age " +
                "and s.metadata.state = :state");
        query.setParameter("age",age );
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationSchema) query.getSingleResult();
    }

    @Override
    public DexEvaluationSchema findSchemaByOrdinal(Integer ordinal) {
        Query query = entityManager.createQuery("select s from DexEvaluationSchema s" +
                " where s.ordinal =:ordinal " +
                "and s.metadata.state = :state");
        query.setParameter("ordinal",ordinal );
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationSchema) query.getSingleResult();
    }

    @Override
    public DexEvaluationSection findSectionByCode(String code) {
        Query query = entityManager.createQuery("select s from DexEvaluationSection s where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationSection) query.getSingleResult();
    }

    @Override
    public DexEvaluationQuestion findQuestionByCode(String code) {
        Query query = entityManager.createQuery("select s from DexEvaluationQuestion s where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationQuestion) query.getSingleResult();
    }

    @Override
    public DexEvaluationAnswer findAnswerByCode(String code) {
        Query query = entityManager.createQuery("select s from DexEvaluationAnswer s where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEvaluationAnswer) query.getSingleResult();
    }

    @Override
    public List<DexEvaluationSchema> find(String filter, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexEvaluationSchema s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state " +
                "order by s.ordinal asc");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexEvaluationSchema>) query.getResultList();
    }

    @Override
    public List<DexEvaluationSection> findSections(DexEvaluationSchema schema) {
        Query query = entityManager.createQuery("select s from DexEvaluationSection s where " +
                "s.schema = :schema " +
                "and s.metadata.state = :state ");
        query.setParameter("schema", schema);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (List<DexEvaluationSection>) query.getResultList();
    }

    @Override
    public List<DexEvaluationSection> findSections(DexEvaluationSchema schema, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexEvaluationSection s where " +
                "s.schema = :schema " +
                "and s.metadata.state = :state ");
        query.setParameter("schema", schema);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexEvaluationSection>) query.getResultList();
    }

    @Override
    public List<DexEvaluationQuestion> findQuestions(DexEvaluationSection section) {
        Query query = entityManager.createQuery("select s from DexEvaluationQuestion s where " +
                "s.section = :section " +
                "and s.metadata.state = :state ");
        query.setParameter("section", section);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (List<DexEvaluationQuestion>) query.getResultList();
    }

    @Override
    public List<DexEvaluationQuestion> findQuestions(DexEvaluationSection section, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexEvaluationQuestion s where " +
                "s.section = :section " +
                "and s.metadata.state = :state ");
        query.setParameter("section", section);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexEvaluationQuestion>) query.getResultList();
    }

    @Override
    public Integer count(String filter) {
        Query query = entityManager.createQuery("select count(s) from DexEvaluationSchema s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countSection(DexEvaluationSchema schema) {
        Query query = entityManager.createQuery("select count(s) from DexEvaluationSection s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + schema + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countQuestion(DexEvaluationSection section) {
        Query query = entityManager.createQuery("select count(s) from DexEvaluationQuestion s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + section + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countAnswer(DexEvaluationQuestion question) {
        Query query = entityManager.createQuery("select count(s) from DexEvaluationAnswer s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.description) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + question + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }


    public boolean isExists(String code) {
        Query query = entityManager.createQuery("select count(s) from DexEvaluationSchema s where " +
                "s.code = :code " +
                "and s.metadata.state = :state ");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return 0 < ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public void addSection(DexEvaluationSchema schema, DexEvaluationSection section, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(section, "Section cannot be null");
        Assert.notNull(schema, "Schema cannot be null");
        section.setSchema(schema);
        prepareMetadata(section, user, true);
        entityManager.persist(section);
        entityManager.flush();
    }

    @Override
    public void updateSection(DexEvaluationSchema schema, DexEvaluationSection section, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(section, "section cannot be null");
        Assert.notNull(schema, "schema cannot be null");
        section.setSchema(schema);
        prepareMetadata(section, user, true);
        entityManager.merge(section);
        entityManager.flush();


    }

    @Override
    public void deleteSection(DexEvaluationSchema schema, DexEvaluationSection section, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(section, "section cannot be null");
        Assert.notNull(schema, "schema cannot be null");
        section.setSchema(schema);
        prepareMetadata(section, user, false);
        entityManager.merge(section);
        entityManager.flush();

    }

    @Override
    public void addQuestion(DexEvaluationSection section, DexEvaluationQuestion question, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(section, "Section cannot be null");
        Assert.notNull(question, "Question cannot be null");
        question.setSection(section);
        prepareMetadata(question, user, true);
        entityManager.persist(question);
        entityManager.flush();
    }

    @Override
    public void updateQuestion(DexEvaluationSection section, DexEvaluationQuestion question, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(section, "Section cannot be null");
        Assert.notNull(question, "Question cannot be null");
        prepareMetadata(question, user, true);
        question.setSection(section);
        entityManager.merge(question);
        entityManager.flush();

    }

    @Override
    public void deleteQuestion(DexEvaluationSection section, DexEvaluationQuestion question, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(section, "Section cannot be null");
        Assert.notNull(question, "Question cannot be null");
        prepareMetadata(question, user, true);
        question.setSection(section);
        entityManager.merge(question);
        entityManager.flush();
    }

    @Override
    public void addAnswer(DexEvaluationQuestion question, DexEvaluationAnswer answer, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(question, "Section cannot be null");
        Assert.notNull(answer, "Question cannot be null");
        answer.setQuestion(question);
        prepareMetadata(answer, user, true);
        entityManager.persist(answer);
        entityManager.flush();
    }

    @Override
    public void updateAnswer(DexEvaluationQuestion question, DexEvaluationAnswer answer, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(question, "Section cannot be null");
        Assert.notNull(answer, "Question cannot be null");
        prepareMetadata(answer, user, true);
        answer.setQuestion(question);
        entityManager.merge(answer);
        entityManager.flush();

    }


    @Override
    public void deleteAnswer(DexEvaluationQuestion question, DexEvaluationAnswer answer, DexUser user) {
        Assert.notNull(user, "User cannot be null");
        Assert.notNull(question, "Section cannot be null");
        Assert.notNull(answer, "Question cannot be null");
        prepareMetadata(answer, user, true);
        answer.setQuestion(question);
        entityManager.merge(answer);
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
