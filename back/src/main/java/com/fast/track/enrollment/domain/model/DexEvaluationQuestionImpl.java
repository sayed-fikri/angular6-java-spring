package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;

import javax.persistence.*;

import java.util.List;

@Entity(name = "DexEvaluationQuestion")
@Table(name = "DEX_EVLN_QSTN")
public class DexEvaluationQuestionImpl implements DexEvaluationQuestion {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_EVLN_QSTN")
    @SequenceGenerator(name = "SQ_DEX_EVLN_QSTN", sequenceName = "SQ_DEX_EVLN_QSTN", allocationSize = 1)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATEMENT", columnDefinition = "text")
    private String statement;

    @ManyToOne(targetEntity = DexEvaluationSectionImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SECTION_ID", nullable = true)
    private DexEvaluationSection section;

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
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getStatement() {
        return statement;
    }

    @Override
    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public DexEvaluationSection getSection() {
        return section;
    }

    @Override
    public void setSection(DexEvaluationSection section) {
        this.section = section;
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
        return DexEvaluationQuestion.class;
    }
}

