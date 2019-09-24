package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "DexCalibration")
@Table(name = "DEX_CLBR")
public class DexCalibrationImpl implements DexCalibration {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_CALIBRATION")
    @SequenceGenerator(name = "SQ_DEX_CALIBRATION", sequenceName = "SQ_DEX_CALIBRATION", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TOTAL_SCORE")
    private Integer totalScore;


    @OneToOne(targetEntity = DexEvaluationSchemaImpl.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "SCHEMA_ID")
    private DexEvaluationSchema schema;

    @OneToOne(targetEntity = DexChildImpl.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CHILD_ID", nullable = false)
    private DexChild child;

    @OneToOne(targetEntity = DexEvaluationStatusImpl.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "EVLN_STAT_ID")
    private DexEvaluationStatus evaluationStatus;

    @OneToMany(targetEntity = DexCalibrationSectionResponseImpl.class, mappedBy = "calibration", fetch = FetchType.LAZY)
    private List<DexCalibrationSectionResponse> sectionResponses;

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
    public String getDescription() { return description; }

    @Override
    public void setDescription(String description) { this.description = description; }

    @Override
    public Integer getTotalScore() { return totalScore; }

    @Override
    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }

    @Override
    public DexEvaluationSchema getSchema() { return schema; }

    @Override
    public void setSchema(DexEvaluationSchema schema) { this.schema = schema; }

    @Override
    public DexChild getChild() { return child; }

    @Override
    public void setChild(DexChild child) { this.child = child; }

    @Override
    public DexEvaluationStatus getEvaluationStatus() { return evaluationStatus; }

    @Override
    public void setEvaluationStatus(DexEvaluationStatus evaluationStatus) { this.evaluationStatus = evaluationStatus; }


    @Override
    public List<DexCalibrationSectionResponse> getSectionResponses() { return sectionResponses;}

    @Override
    public void setSectionResponses(List<DexCalibrationSectionResponse> sectionResponses) {
        this.sectionResponses = sectionResponses;
    }

    @Override
    public DexMetadata getMetadata() { return metadata; }

    @Override
    public void setMetadata(DexMetadata metadata) { this.metadata = metadata; }

    @Override
    public Class<?> getInterfaceClass() {
        return DexCalibration.class;
    }
}




