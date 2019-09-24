package com.irichment.enrollment.api.vo;

import com.irichment.core.api.MetaObject;

public class ResultHistory extends MetaObject {

    private String ageRange;
    private Integer totalScore;
    private Integer expectedScore;

    public String getAgeRange() { return ageRange; }

    public void setAgeRange(String ageRange) { this.ageRange = ageRange; }

    public Integer getTotalScore() { return totalScore; }

    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }

    public Integer getExpectedScore() { return expectedScore; }

    public void setExpectedScore(Integer expectedScore) { this.expectedScore = expectedScore; }
}
