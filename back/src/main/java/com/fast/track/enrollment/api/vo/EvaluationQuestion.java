package com.irichment.enrollment.api.vo;

import com.irichment.core.api.MetaObject;

public class EvaluationQuestion extends MetaObject {

    private String code;
    private String description;
    private String statement;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
