package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "DexCoupon")
@Table(name = "DEX_COPN")
public class DexCouponImpl implements DexCoupon {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_COPN")
    @SequenceGenerator(name = "SQ_DEX_COPN", sequenceName = "SQ_DEX_COPN", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "REDEEMED")
    private Boolean redeemed;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne(targetEntity = DexEnrollmentImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ENROLLMENT_ID")
    private DexEnrollment enrollment;

    @Embedded
    private DexMetadata metadata;

    @Override
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    @Override
    public String getCode() { return code; }

    @Override
    public void setCode(String code) { this.code = code; }

    public Boolean getRedeemed() { return redeemed; }

    public void setRedeemed(Boolean redeemed) { this.redeemed = redeemed; }

    @Override
    public String getDescription() { return description; }

    @Override
    public void setDescription(String description) { this.description = description; }

    @Override
    public DexEnrollment getEnrollment() { return enrollment; }

    @Override
    public void setEnrollment(DexEnrollment enrollment) { this.enrollment = enrollment; }

    @Override
    public DexMetadata getMetadata() { return metadata; }

    @Override
    public void setMetadata(DexMetadata metadata) { this.metadata = metadata; }

    @Override
    public Class<?> getInterfaceClass() {
        return DexCoupon.class;
    }
}




