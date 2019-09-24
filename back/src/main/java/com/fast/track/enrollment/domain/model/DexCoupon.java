package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetadata;

public interface DexCoupon extends DexMetaObject {

    String getCode();

    void setCode(String code);

    Boolean getRedeemed();

    void setRedeemed(Boolean redeemed);

    String getDescription();

    void setDescription(String description);

    DexEnrollment getEnrollment();

    void setEnrollment(DexEnrollment enrollment);
}
