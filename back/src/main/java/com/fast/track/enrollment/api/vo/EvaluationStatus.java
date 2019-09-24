package com.irichment.enrollment.api.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irichment.core.api.MetaObject;

import java.io.IOException;

public class EvaluationStatus extends MetaObject {

    private Integer min;
    private Integer max;
    private Schema schema;
    private String status;
    private StatusType statusType;


    public Integer getMin() { return min; }

    public void setMin(Integer min) { this.min = min; }

    public Integer getMax() { return max; }

    public void setMax(Integer max) { this.max = max; }

    public Schema getSchema() { return schema; }

    public void setSchema(Schema schema) { this.schema = schema; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public StatusType getStatusType() { return statusType; }

    public void setStatusType(StatusType statusType) { this.statusType = statusType; }
}

