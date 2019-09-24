package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;

import javax.persistence.*;

@Entity(name = "DexEvaluationQuestionResponse")
@Table(name = "DEX_EVLN_QSTN_RSPN")
public class DexEvaluationQuestionResponseImpl implements DexEvaluationQuestionResponse {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_EVLN_QSTN_RSPN")
    @SequenceGenerator(name = "SQ_DEX_EVLN_QSTN_RSPN", sequenceName = "SQ_DEX_EVLN_QSTN_RSPN", allocationSize = 1)
    private Long id;

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "QUESTION_ANSWER",nullable = false)
    private DexAnswerType questionAnswer;

    @Column(name = "QUESTION_SCORE",nullable = false)
    private Integer questionScore;

    @OneToOne(targetEntity = DexEvaluationQuestionImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private DexEvaluationQuestion question;

    @ManyToOne(targetEntity = DexEvaluationSectionResponseImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "RESPONSE_ID")
    private DexEvaluationSectionResponse sectionResponse;

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
    public DexAnswerType getQuestionAnswer() { return questionAnswer; }

    @Override
    public void setQuestionAnswer(DexAnswerType questionAnswer) { this.questionAnswer = questionAnswer; }

    @Override
    public Integer getQuestionScore() { return questionScore; }

    @Override
    public void setQuestionScore(Integer questionScore) { this.questionScore = questionScore; }

    @Override
    public DexEvaluationQuestion getQuestion() { return question; }

    @Override
    public void setQuestion(DexEvaluationQuestion question) { this.question = question; }

    @Override
    public DexEvaluationSectionResponse getSectionResponse() { return sectionResponse; }

    @Override
    public void setSectionResponse(DexEvaluationSectionResponse sectionResponse) { this.sectionResponse = sectionResponse; }

    @Override
    public DexMetadata getMetadata() { return metadata; }

    @Override
    public void setMetadata(DexMetadata metadata) { this.metadata = metadata; }

    @Override
    public Class<?> getInterfaceClass() {
        return DexEvaluationQuestionResponse.class;
    }
}




