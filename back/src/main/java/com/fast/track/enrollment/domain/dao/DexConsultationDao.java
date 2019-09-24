package com.irichment.enrollment.domain.dao;


import com.irichment.core.domain.GenericDao;
import com.irichment.enrollment.domain.model.DexChild;
import com.irichment.enrollment.domain.model.DexComment;
import com.irichment.enrollment.domain.model.DexConsultation;
import com.irichment.enrollment.domain.model.DexEnrollment;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexUser;

import java.util.List;

public interface DexConsultationDao extends GenericDao<Long, DexConsultation> {

    // ====================================================================================================
    // FINDER
    // ====================================================================================================

    DexConsultation findByCode(String code);

    DexConsultation findByChildAndTherapist(DexChild child,DexTherapist therapist);

    DexComment findCommentByCode(String code);

    List<DexConsultation> find(String filter, Integer offset, Integer limit);

    List<DexConsultation> findByChild(DexChild child, Integer offset , Integer limit);

    List<DexConsultation> findByChild(DexChild child);

    List<DexConsultation> findByTherapist(DexTherapist therapist, Integer offset,Integer limit);

    List<DexConsultation> findByTherapist(DexTherapist therapist);

    List<DexComment> findComments(DexConsultation consultation, Integer offset, Integer limit);



    // ====================================================================================================
    // HELPER
    // ====================================================================================================
    Integer count(String filter);

    Integer countByChild(DexChild child);

    Integer countByTherapist(DexTherapist therapist);

    Integer countComment(DexConsultation consultation);

    void addComment(DexConsultation consultation, DexComment comment, DexUser user);

    void updateComment(DexComment comment, DexUser user);

    void deleteComment(DexComment comment, DexUser user);

    boolean isExists(String code);

}
