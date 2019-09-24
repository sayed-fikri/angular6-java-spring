package com.irichment.enrollment.domain.dao;

import com.irichment.core.domain.GenericDao;
import com.irichment.enrollment.domain.model.DexEvaluationAnswer;
import com.irichment.enrollment.domain.model.DexEvaluationQuestion;
import com.irichment.enrollment.domain.model.DexEvaluationSchema;
import com.irichment.enrollment.domain.model.DexEvaluationSection;
import com.irichment.identity.domain.model.DexUser;

import java.util.List;

public interface DexEvaluationSchemaDao extends GenericDao<Long, DexEvaluationSchema> {

    // finder
    DexEvaluationSection findSectionById(Long id);

    DexEvaluationQuestion findQuestionById(Long id);

    DexEvaluationAnswer findAnswerById(Long id);

    DexEvaluationSchema findByCode(String code);

    DexEvaluationSchema findSchemaByAge(Integer age);

    DexEvaluationSchema findSchemaByOrdinal(Integer ordinal);

    DexEvaluationSection findSectionByCode(String code);

    DexEvaluationQuestion findQuestionByCode(String code);

    DexEvaluationAnswer findAnswerByCode(String code);

    List<DexEvaluationSchema> find(String filter, Integer offset, Integer limit);

    List<DexEvaluationSection> findSections(DexEvaluationSchema schema);

    List<DexEvaluationSection> findSections(DexEvaluationSchema schema, Integer offset, Integer limit);

    List<DexEvaluationQuestion> findQuestions(DexEvaluationSection section);

    List<DexEvaluationQuestion> findQuestions(DexEvaluationSection section, Integer offset, Integer limit);

    // helper
    Integer count(String filter);

    Integer countSection(DexEvaluationSchema schema);

    Integer countQuestion(DexEvaluationSection section);

    Integer countAnswer(DexEvaluationQuestion question);

    boolean isExists(String code);

    // cruds

    void addSection(DexEvaluationSchema schema, DexEvaluationSection section, DexUser user);

    void updateSection(DexEvaluationSchema schema, DexEvaluationSection section, DexUser user);

    void deleteSection(DexEvaluationSchema schema, DexEvaluationSection section, DexUser user);

    void addQuestion(DexEvaluationSection section, DexEvaluationQuestion question, DexUser user);

    void updateQuestion(DexEvaluationSection section, DexEvaluationQuestion question, DexUser user);

    void deleteQuestion(DexEvaluationSection section, DexEvaluationQuestion question, DexUser user);

    void addAnswer(DexEvaluationQuestion question, DexEvaluationAnswer answer, DexUser user);

    void updateAnswer(DexEvaluationQuestion question, DexEvaluationAnswer answer, DexUser user);

    void deleteAnswer(DexEvaluationQuestion question, DexEvaluationAnswer answer, DexUser user);


}
