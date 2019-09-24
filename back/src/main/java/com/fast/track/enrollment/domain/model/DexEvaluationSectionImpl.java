package com.irichment.enrollment.domain.model;


import com.irichment.core.domain.DexMetadata;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "DexEvaluationSection")
@Table (name ="DEX_EVLN_SCTN")
public class DexEvaluationSectionImpl implements DexEvaluationSection {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_EVLN_SCTN")
    @SequenceGenerator(name = "SQ_DEX_EVLN_SCTN", sequenceName = "SQ_DEX_EVLN_SCTN", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;


    @ManyToOne(targetEntity = DexEvaluationSchemaImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEMA_ID")
    private DexEvaluationSchema schema;

    @OneToMany(targetEntity = DexEvaluationQuestionImpl.class, mappedBy = "section", fetch = FetchType.LAZY)
    private List<DexEvaluationQuestion> questions;

    @Embedded
    private DexMetadata metadata;

    @Override
    public Long getId() { return id; }

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
    public DexEvaluationSchema getSchema() {
        return schema;
    }

    @Override
    public void setSchema(DexEvaluationSchema schema) {
        this.schema = schema;
    }


    public List<DexEvaluationQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<DexEvaluationQuestion> questions) {
        this.questions = questions;
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
        return DexEvaluationSection.class;
    }
}
