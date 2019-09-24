package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;

import javax.persistence.*;
import java.util.List;

@Entity(name = "DexCalibrationSectionResponse")
@Table(name = "DEX_CLBR_SCTN_RSPN")
public class DexCalibrationSectionResponseImpl implements DexCalibrationSectionResponse {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_CLBR_SCTN_RSPN")
    @SequenceGenerator(name = "SQ_DEX_CLBR_SCTN_RSPN", sequenceName = "SQ_DEX_CLBR_SCTN_RSPN", allocationSize = 1)
    private Long id;

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SECTION_SCORE")
    private Integer sectionScore;

    @Column(name = "FULL_SCORE")
    private Integer fullScore;

    @OneToOne(targetEntity = DexEvaluationSectionImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SECTION_ID", nullable = false)
    private DexEvaluationSection section;

    @ManyToOne(targetEntity = DexCalibrationImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CALIBRATION_ID")
    private DexCalibration calibration;

    @OneToMany(targetEntity = DexCalibrationQuestionResponseImpl.class, mappedBy = "sectionResponse", fetch = FetchType.LAZY)
    private List<DexCalibrationQuestionResponse> questionResponses;

    @Embedded
    private DexMetadata metadata;

    @Override
    public DexMetadata getMetadata() { return this.metadata; }

    @Override
    public void setMetadata(DexMetadata metadata) { this.metadata = metadata; }

    @Override
    public Class<?> getInterfaceClass() {
        return DexCalibrationSectionResponse.class;
    }

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
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Integer getSectionScore() {
        return sectionScore;
    }

    @Override
    public void setSectionScore(Integer sectionScore) {
        this.sectionScore = sectionScore;
    }

    @Override
    public Integer getFullScore() { return fullScore; }

    @Override
    public void setFullScore(Integer fullScore) { this.fullScore = fullScore; }

    @Override
    public DexEvaluationSection getSection() {
        return section;
    }

    @Override
    public void setSection(DexEvaluationSection section) {
        this.section = section;
    }

    @Override
    public DexCalibration getCalibration() {
        return calibration;
    }

    @Override
    public void setCalibration(DexCalibration calibration) {
        this.calibration = calibration;
    }

    @Override
    public List<DexCalibrationQuestionResponse> getQuestionResponses() {
        return questionResponses;
    }

    @Override
    public void setQuestionResponses(List<DexCalibrationQuestionResponse> questionResponses) {
        this.questionResponses = questionResponses;
    }
}




