package com.irichment.enrollment.api.vo;

import com.irichment.core.api.MetaObject;
import com.irichment.identity.api.vo.Guardian;
import com.irichment.identity.api.vo.Therapist;
import com.irichment.learning.api.vo.Course;

import java.util.List;

public class Consultation extends MetaObject {

    private String code;
    private Child child;
    private Therapist therapist;
    private List<Comment> comments;

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public Child getChild() { return child; }

    public void setChild(Child child) { this.child = child; }

    public Therapist getTherapist() { return therapist; }

    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) { this.comments = comments; }

    public void setTherapist(Therapist therapist) { this.therapist = therapist; }
}
