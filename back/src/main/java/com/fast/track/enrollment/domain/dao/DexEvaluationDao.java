package com.irichment.enrollment.domain.dao;

import com.irichment.core.domain.GenericDao;
import com.irichment.enrollment.domain.model.*;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexUser;

import java.util.List;

public interface DexEvaluationDao extends GenericDao<Long, DexEvaluation> {

    //finder

    DexEvaluation findEvaluationByCode(String code);

    DexEvaluation findEvaluationByChild(DexChild child);

    DexEvaluationStatus findEvaluationStatusBySchemaAndTotalScore(DexEvaluationSchema schema,Integer totalScore);

    DexEvaluationSectionResponse findSectionResponseById(Long id);

    DexEvaluationQuestionResponse findQuestionResponseById(Long id);

    DexEvaluationSectionResponse findSectionResponseByCode(String code);

    DexEvaluationQuestionResponse findQuestionResponseByCode(String code);

    DexEvaluationQuestionResponse findQuestionResponseBySectionResponse(DexEvaluationSectionResponse sectionResponse);

    List<DexEvaluationSectionResponse> findSectionResponseByEvaluation(DexEvaluation evaluation);

    List<DexEvaluation> findEvaluations(String filter, Integer offset, Integer limit);

    List<DexEvaluation> findEvaluationsByChild(DexChild child);

    List<DexEvaluation> findEvaluationsByGuardian(DexGuardian guardian, Integer offset, Integer limit);

    List<DexEvaluationSectionResponse> findSectionResponses(DexEvaluation evaluation ,
                                                            Integer offset, Integer limit);

    List<DexEvaluationQuestionResponse> findQuestionResponses(DexEvaluationSectionResponse sectionResponse);

    List<DexEvaluationQuestionResponse> findQuestionResponses(DexEvaluationSectionResponse sectionResponse,
                                                              Integer offset, Integer limit);


    //helper
    Integer count(String filter);

    Integer count(DexGuardian guardian);

    Integer countSectionResponse(DexEvaluation evaluation);

    Integer countQuestionResponse(DexEvaluationSectionResponse schemaResponse);

    boolean isExists(String code);


    //cruds

    void addEvaluation(DexEvaluation evaluation,DexUser user);

    void addSectionResponse(DexEvaluation evaluation,
                            DexEvaluationSectionResponse sectionResponse, DexUser user);

    void addQuestionResponse(DexEvaluationSectionResponse sectionResponse,
                             DexEvaluationQuestionResponse questionResponse, DexUser user);

    void updateEvaluation(DexEvaluation evaluation,DexUser user);

    void updateSectionResponse(DexEvaluation evaluation,
                               DexEvaluationSectionResponse sectionResponse, DexUser user);

    void updateSectionResponse(DexCalibration calibration,
                               DexEvaluationSectionResponse sectionResponse, DexUser user);

    void updateQuestionResponse(DexEvaluationQuestionResponse questionResponse, DexUser user);

    void deleteEvaluation(DexEvaluation evaluation, DexUser user);

    void deleteSectionResponse(DexEvaluation evaluation,
                               DexEvaluationSectionResponse sectionResponse, DexUser user);

    void deleteQuestionResponse(DexEvaluationSectionResponse sectionResponse,
                                DexEvaluationQuestionResponse questionResponse, DexUser user);


    void prepareMetadata(Object i, DexUser user, boolean active);
}
