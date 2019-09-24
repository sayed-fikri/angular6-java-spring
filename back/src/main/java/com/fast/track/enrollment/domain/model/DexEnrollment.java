package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetadata;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.learning.domain.model.DexCourse;

import java.util.List;

public interface DexEnrollment extends DexMetaObject {

    String getCode();

    void setCode(String code);

    Boolean getPaid();

    void setPaid(Boolean paid);

    String getBillNo();

    void setBillNo(String billNo);

    String getDescription();

    void setDescription(String description);

    DexGuardian getGuardian();

    void setGuardian(DexGuardian guardian);

    DexChild getChild();

    void setChild(DexChild child);


    DexCoupon getCoupon();

    void setCoupon(DexCoupon coupon);

    DexCourse getCourse1();

    void setCourse1(DexCourse course1);

    DexCourse getCourse2();

    void setCourse2(DexCourse course2);

    DexCourse getCourse3();

    void setCourse3(DexCourse course3);

    @Override
    DexMetadata getMetadata();
}
