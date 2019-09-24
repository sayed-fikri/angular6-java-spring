package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.identity.domain.model.DexTherapist;

import java.util.List;

public interface DexConsultation extends DexMetaObject {
    String getCode();

    void setCode(String code);

    DexTherapist getTherapist();

    void setTherapist(DexTherapist therapist);

    DexChild getChild();

    void setChild(DexChild child);

    List<DexComment> getComments();

    void setComments(List<DexComment> comments);
}
