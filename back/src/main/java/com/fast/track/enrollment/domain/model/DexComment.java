package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetadata;

public interface DexComment extends DexMetaObject {
    @Override
    Long getId();

    String getCode();

    void setCode(String code);

    String getComment();

    void setComment(String comment);

    DexConsultation getConsultation();

    void setConsultation(DexConsultation consultation);

    @Override
    DexMetadata getMetadata();

    @Override
    void setMetadata(DexMetadata metadata);

    @Override
    Class<?> getInterfaceClass();
}
