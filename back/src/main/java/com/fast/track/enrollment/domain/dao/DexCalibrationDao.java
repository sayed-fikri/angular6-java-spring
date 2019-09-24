package com.irichment.enrollment.domain.dao;

import com.irichment.core.domain.GenericDao;
import com.irichment.enrollment.domain.model.*;
import com.irichment.identity.domain.model.DexUser;

import java.util.List;

public interface DexCalibrationDao extends GenericDao<Long, DexCalibration> {

    //finder

    DexCalibration findCalibrationByCode(String code);

    DexCalibration findCalibrationByChild(DexChild child);

    DexEvaluationStatus findEvaluationStatusBySchemaAndTotalScore(DexEvaluationSchema schema, Integer totalScore);

    DexCalibrationSectionResponse findSectionResponseById(Long id);

    DexCalibrationQuestionResponse findQuestionResponseById(Long id);

    DexCalibrationSectionResponse findSectionResponseByCode(String code);

    DexCalibrationQuestionResponse findQuestionResponseByCode(String code);

    DexCalibrationQuestionResponse findQuestionResponseBySectionResponse(DexCalibrationSectionResponse sectionResponse);

    List<DexCalibrationSectionResponse> findSectionResponseByCalibration(DexCalibration calibration);

    List<DexCalibration> findCalibrations(String filter, Integer offset, Integer limit);

    List<DexCalibrationSectionResponse> findSectionResponses(DexCalibration calibration,
                                                            Integer offset, Integer limit);

    List<DexCalibrationQuestionResponse> findQuestionResponses(DexCalibrationSectionResponse sectionResponse);

    List<DexCalibrationQuestionResponse> findQuestionResponses(DexCalibrationSectionResponse sectionResponse,
                                                              Integer offset, Integer limit);


    //helper
    Integer count(String filter);

    Integer countSectionResponse(DexCalibration calibration);

    Integer countQuestionResponse(DexCalibrationSectionResponse schemaResponse);

    boolean isExists(String code);


    //cruds

    void addCalibration(DexCalibration calibration, DexUser user);

    void addSectionResponse(DexCalibration calibration,
                            DexCalibrationSectionResponse sectionResponse, DexUser user);

    void addQuestionResponse(DexCalibrationSectionResponse sectionResponse,
                             DexCalibrationQuestionResponse questionResponse, DexUser user);

    void updateCalibration(DexCalibration calibration, DexUser user);

    void updateSectionResponse(DexCalibration calibration,
                               DexCalibrationSectionResponse sectionResponse, DexUser user);

    void updateQuestionResponse(DexCalibrationQuestionResponse questionResponse, DexUser user);

    void deleteCalibration(DexCalibration calibration, DexUser user);

    void deleteSectionResponse(DexCalibration calibration,
                               DexCalibrationSectionResponse sectionResponse, DexUser user);

    void deleteQuestionResponse(DexCalibrationSectionResponse sectionResponse,
                                DexCalibrationQuestionResponse questionResponse, DexUser user);


}
