package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetadata;

public interface DexEvaluationStatus extends DexMetaObject {

    Integer getMin();

    void setMin(Integer min);

    Integer getMax();

    void setMax(Integer max);

    String getStatus();

    void setStatus(String status);

    DexStatusType getStatusType();

    void setStatusType(DexStatusType statusType);

    @Override
    DexMetadata getMetadata();

    @Override
    void setMetadata(DexMetadata metadata);

    DexEvaluationSchema getSchema();

    void setSchema(DexEvaluationSchema schema);

    @Override
    Class<?> getInterfaceClass();
}
