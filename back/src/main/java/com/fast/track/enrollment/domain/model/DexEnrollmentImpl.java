package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexGuardianImpl;
import com.irichment.learning.domain.model.DexCourse;
import com.irichment.learning.domain.model.DexCourseImpl;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "DexEnrollment")
@Table(name = "DEX_ENMT")
public class DexEnrollmentImpl implements DexEnrollment {


    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_ENMT")
    @SequenceGenerator(name = "SQ_DEX_ENMT", sequenceName = "SQ_DEX_ENMT", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "PAID")
    private Boolean paid;

    @Column(name = "BILL_NO")
    private String billNo;

    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @JoinColumn(name = "GUARDIAN_ID", nullable = false)
    @ManyToOne(targetEntity = DexGuardianImpl.class)
    private DexGuardian guardian;

    @NotNull
    @JoinColumn(name = "CHILD_ID", nullable = false)
    @ManyToOne(targetEntity = DexChildImpl.class)
    private DexChild child;

    @OneToOne(targetEntity = DexCouponImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "COUPON_ID")
    private DexCoupon coupon;

    @OneToOne(targetEntity = DexCourseImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE1_ID")
    private DexCourse course1;

    @OneToOne(targetEntity = DexCourseImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE2_ID")
    private DexCourse course2;

    @OneToOne(targetEntity = DexCourseImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE3_ID")
    private DexCourse course3;



    @Embedded
    private DexMetadata metadata;


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Boolean getPaid() { return paid; }

    @Override
    public void setPaid(Boolean paid) { this.paid = paid;}


    @Override
    public String getBillNo() { return billNo; }

    @Override
    public void setBillNo(String billNo) { this.billNo = billNo;}

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public DexGuardian getGuardian() {
        return guardian;
    }

    @Override
    public void setGuardian(DexGuardian guardian) {
        this.guardian = guardian;
    }

    @Override
    public DexChild getChild() {
        return child;
    }

    @Override
    public void setChild(DexChild child) {
        this.child = child;
    }

    @Override
    public DexCoupon getCoupon() { return coupon; }

    @Override
    public void setCoupon(DexCoupon coupon) { this.coupon = coupon; }

    @Override
    public DexCourse getCourse1() { return course1; }

    @Override
    public void setCourse1(DexCourse course1) { this.course1 = course1; }

    @Override
    public DexCourse getCourse2() { return course2; }

    @Override
    public void setCourse2(DexCourse course2) { this.course2 = course2; }

    @Override
    public DexCourse getCourse3() { return course3; }

    @Override
    public void setCourse3(DexCourse course3) { this.course3 = course3; }

    @Override
    public DexMetadata getMetadata() {
        return this.metadata;
    }

    @Override
    public void setMetadata(DexMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public Class<?> getInterfaceClass() {
        return DexEnrollment.class;
    }

}




