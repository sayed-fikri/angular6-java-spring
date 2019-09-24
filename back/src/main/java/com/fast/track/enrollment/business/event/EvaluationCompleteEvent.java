package com.irichment.enrollment.business.event;

import com.irichment.enrollment.domain.model.DexEvaluation;
import org.springframework.context.ApplicationEvent;

public class EvaluationCompleteEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    private DexEvaluation evaluation;

    public EvaluationCompleteEvent(DexEvaluation source) {
        super(source);
        this.evaluation = source;
    }

    public DexEvaluation getEvaluation() {
        return evaluation;
    }
}
