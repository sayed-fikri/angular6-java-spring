package com.irichment.enrollment.api.vo;

import com.irichment.core.api.MetaObject;
import com.irichment.identity.api.vo.Therapist;

public class Comment extends MetaObject {

    private String code;
    private String comment;
    private Consultation consultation;

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public Consultation getConsultation() { return consultation; }

    public void setConsultation(Consultation consultation) { this.consultation = consultation; }
}
