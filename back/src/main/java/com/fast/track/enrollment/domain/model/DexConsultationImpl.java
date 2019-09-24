package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexGuardianImpl;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexTherapistImpl;
import com.irichment.learning.domain.model.DexCourse;
import com.irichment.learning.domain.model.DexCourseImpl;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "DexConsultation")
@Table(name = "DEX_CSTN")
public class DexConsultationImpl implements DexConsultation {


    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_CSTN")
    @SequenceGenerator(name = "SQ_DEX_CSTN", sequenceName = "SQ_DEX_CSTN", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @JoinColumn(name = "THERAPIST_ID")
    @ManyToOne(targetEntity = DexTherapistImpl.class)
    private DexTherapist therapist;

    @NotNull
    @JoinColumn(name = "CHILD_ID", nullable = false)
    @ManyToOne(targetEntity = DexChildImpl.class)
    private DexChild child;

    @OneToMany(targetEntity = DexCommentImpl.class, mappedBy = "consultation", fetch = FetchType.LAZY)
    private List<DexComment> comments;

    @Embedded
    private DexMetadata metadata;

    @Override
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    @Override
    public String getCode() { return code; }

    @Override
    public void setCode(String code) { this.code = code; }

    @Override
    public DexTherapist getTherapist() { return therapist; }

    @Override
    public void setTherapist(DexTherapist therapist) { this.therapist = therapist; }

    @Override
    public DexChild getChild() { return child; }

    @Override
    public void setChild(DexChild child) { this.child = child; }

    @Override
    public List<DexComment> getComments() {
        return comments;
    }

    @Override
    public void setComments(List<DexComment> comments) {
        this.comments = comments;
    }

    @Override
    public DexMetadata getMetadata() { return metadata; }

    @Override
    public void setMetadata(DexMetadata metadata) { this.metadata = metadata; }


    @Override
    public Class<?> getInterfaceClass() {
        return DexConsultation.class;
    }

}




