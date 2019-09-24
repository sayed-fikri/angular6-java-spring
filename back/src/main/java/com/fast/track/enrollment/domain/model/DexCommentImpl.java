package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexTherapistImpl;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "DexComment")
@Table(name = "DEX_CMMT")
public class DexCommentImpl implements DexComment {


    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_CMMT")
    @SequenceGenerator(name = "SQ_DEX_CMMT", sequenceName = "SQ_DEX_CMMT", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "COMMENT", columnDefinition = "text")
    private String comment;

    @NotNull
    @JoinColumn(name = "CONSULTATION_ID", nullable = false)
    @ManyToOne(targetEntity = DexConsultationImpl.class)
    private DexConsultation consultation;

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
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public DexConsultation getConsultation() {
        return consultation;
    }

    @Override
    public void setConsultation(DexConsultation consultation) {
        this.consultation = consultation;
    }

    @Override
    public DexMetadata getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(DexMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public Class<?> getInterfaceClass() {
        return DexComment.class;
    }

}




