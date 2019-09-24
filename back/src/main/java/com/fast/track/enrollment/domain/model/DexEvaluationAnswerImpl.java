package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;

import javax.persistence.*;


@Entity(name = "DexEvaluationAnswer")
@Table(name = "DEX_EVLN_ANSR")
public class DexEvaluationAnswerImpl implements DexEvaluationAnswer{

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_EVLN_ANSR")
    @SequenceGenerator(name = "SQ_DEX_EVLN_ANSR", sequenceName = "SQ_DEX_EVLN_ANSR", allocationSize = 1)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "QUESTIONANSWER", nullable = false)
    private DexAnswerType questionAnswer;

    @Column(name = "ANSWERSCORE")
    private Integer answerScore;

    @Embedded
    private DexMetadata metadata;

    @ManyToOne(targetEntity = DexEvaluationQuestionImpl.class,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID", nullable = true)
    private DexEvaluationQuestion question;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    @Override
    public DexMetadata getMetadata() { return metadata; }

    @Override
    public void setMetadata(DexMetadata metadata) { this.metadata = metadata;}

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
    public Integer getAnswerScore() { return answerScore; }

    @Override
    public void setAnswerScore(Integer answerScore) { this.answerScore = answerScore; }

    @Override
    public DexEvaluationQuestion getQuestion() { return question; }

    @Override
    public void setQuestion(DexEvaluationQuestion question) { this.question = question; }

    @Override
    public Class<?> getInterfaceClass() { return DexEvaluationAnswer.class; }
}


