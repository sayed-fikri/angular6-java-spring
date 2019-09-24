package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;

import javax.persistence.*;

import java.util.List;

@Entity(name = "DexEvaluationSectionResponse")
@Table(name = "DEX_EVLN_SCTN_RSPN")
public class DexEvaluationSectionResponseImpl implements DexEvaluationSectionResponse {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_EVLN_SCTN_RSPN")
    @SequenceGenerator(name = "SQ_DEX_EVLN_SCTN_RSPN", sequenceName = "SQ_DEX_EVLN_SCTN_RSPN", allocationSize = 1)
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

    @ManyToOne(targetEntity = DexEvaluationImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "EVALUATION_ID")
    private DexEvaluation evaluation;

    @OneToMany(targetEntity = DexEvaluationQuestionResponseImpl.class, mappedBy = "sectionResponse", fetch = FetchType.LAZY)
    private List<DexEvaluationQuestionResponse> questionResponses;

    @Embedded
    private DexMetadata metadata;

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
        return DexEvaluationSectionResponse.class;
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
    public DexEvaluation getEvaluation() {
        return evaluation;
    }

    @Override
    public void setEvaluation(DexEvaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public List<DexEvaluationQuestionResponse> getQuestionResponses() {
        return questionResponses;
    }

    @Override
    public void setQuestionResponses(List<DexEvaluationQuestionResponse> questionResponses) {
        this.questionResponses = questionResponses;
    }
}




