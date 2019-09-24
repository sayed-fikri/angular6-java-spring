package com.irichment.enrollment.api.vo;

import com.irichment.core.api.MetaObject;
import com.irichment.identity.api.vo.Guardian;
import com.irichment.identity.api.vo.Therapist;

import java.util.List;

public class EvaluationLog extends MetaObject {

    private Child child;
    private Therapist therapist;
    private Evaluation evaluation;

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Therapist getTherapist() {
        return therapist;
    }

    public void setTherapist(Therapist therapist) {
        this.therapist = therapist;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
