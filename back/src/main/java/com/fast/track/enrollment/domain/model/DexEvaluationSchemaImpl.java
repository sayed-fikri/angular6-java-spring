package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "DexEvaluationSchema")
@Table(name = "DEX_EVLN_SCHM")
public class DexEvaluationSchemaImpl implements DexEvaluationSchema{

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_EVALUATION_SCHEMA")
    @SequenceGenerator(name = "SQ_DEX_EVALUATION_SCHEMA", sequenceName = "SQ_DEX_EVALUATION_SCHEMA", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ORDINAL")
    private Integer ordinal;

    @Column(name= "MIN_AGE")
    private Integer minAge;

    @Column(name= "MAX_AGE")
    private Integer maxAge;

    @Column(name= "TOTAL_QUESTION")
    private Integer totalQuestion;

    @Column(name = "EXPECTED_SCORE")
    private Integer expectedScore;

    @Column(name= "FULL_SCORE")
    private Integer fullScore;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "SCHEMA", nullable = false)
    private DexSchemaType schema;

    @OneToMany(targetEntity = DexEvaluationSectionImpl.class, mappedBy = "schema", fetch = FetchType.LAZY)
    private List<DexEvaluationSection> sections;

    @Embedded
    private DexMetadata metadata;

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
    public Integer getOrdinal() {
        return ordinal;
    }

    @Override
    public void setOrdinal(Integer ordinal) { this.ordinal = ordinal; }

    @Override
    public Integer getMinAge() { return minAge; }

    @Override
    public void setMinAge(Integer minAge) { this.minAge = minAge; }

    @Override
    public Integer getMaxAge() { return maxAge; }

    @Override
    public void setMaxAge(Integer maxAge) { this.maxAge = maxAge; }

    @Override
    public Integer getExpectedScore() { return expectedScore; }

    @Override
    public void setExpectedScore(Integer expectedScore) { this.expectedScore = expectedScore; }

    @Override
    public DexSchemaType getSchema() { return schema; }

    @Override
    public void setSchema(DexSchemaType schema) { this.schema = schema; }

    @Override
    public Integer getTotalQuestion() { return totalQuestion; }

    @Override
    public void setTotalQuestion(Integer totalQuestion) { this.totalQuestion = totalQuestion; }

    @Override
    public Integer getFullScore() { return fullScore; }

    @Override
    public void setFullScore(Integer fullScore) { this.fullScore = fullScore;}

    @Override
    public DexSchemaType getSchemaType() {
        return schema;
    }

    @Override
    public void setSchemaType(DexSchemaType schemaType) {
        this.schema = schemaType;
    }

    @Override
    public List<DexEvaluationSection> getSections() {
        return sections;
    }

    @Override
    public void setSections(List<DexEvaluationSection> sections) {
        this.sections = sections;
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
        return DexEvaluationSchema.class;
    }
}
