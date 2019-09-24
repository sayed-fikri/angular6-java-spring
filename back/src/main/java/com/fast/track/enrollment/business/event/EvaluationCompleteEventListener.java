package com.irichment.enrollment.business.event;

import com.irichment.enrollment.business.service.EnrollmentService;
import com.irichment.enrollment.domain.model.DexConsultation;
import com.irichment.enrollment.domain.model.DexEvaluation;
import com.irichment.system.business.service.SystemService;
import com.irichment.system.business.util.VelocityEvaluator;
import com.irichment.system.domain.model.DexEmailQueue;
import com.irichment.system.domain.model.DexEmailQueueImpl;
import com.irichment.system.domain.model.DexEmailTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.irichment.DexConstants.COMPLETE_EVALUATION_EMAIL;


@Component
@Transactional
public class EvaluationCompleteEventListener implements ApplicationListener<EvaluationCompleteEvent> {

    private SystemService systemService;
    private VelocityEvaluator velocityEvaluator;
    private Environment env;
    private EnrollmentService enrollmentService;

    public EvaluationCompleteEventListener(SystemService systemService, VelocityEvaluator velocityEvaluator,
                                           Environment env, EnrollmentService enrollmentService) {
        this.systemService = systemService;
        this.velocityEvaluator = velocityEvaluator;
        this.env = env;
        this.enrollmentService = enrollmentService;
    }

    @Override
    public void onApplicationEvent(EvaluationCompleteEvent event) {
        notifyCompleteEvaluation(event);
    }


    private void notifyCompleteEvaluation(EvaluationCompleteEvent event) {
        DexEvaluation evaluation = event.getEvaluation();
        List<DexConsultation> consultations = enrollmentService.findConsultationByChild(event.getEvaluation().getChild());
        DexEmailTemplate template = systemService.findEmailTemplateByCode(COMPLETE_EVALUATION_EMAIL);

        Map<String, Object> velocityModel = new HashMap<>();
        velocityModel.put("name", evaluation.getGuardian().getName());
        velocityModel.put("schema", evaluation.getSchema().getOrdinal());
        velocityModel.put("total", evaluation.getTotalScore());
        velocityModel.put("fullMark", evaluation.getSchema().getFullScore());
        velocityModel.put("childStatus", evaluation.getEvaluationStatus().getStatus());

        String email = "";

        if(consultations!=null) {
            for (DexConsultation consultation : consultations) {
                email = email + "," + consultation.getTherapist().getEmail();
            }
        }

        DexEmailQueue emailQueue = new DexEmailQueueImpl();
        emailQueue.setTo(evaluation.getGuardian().getEmail());

        if(consultations!=null)
        emailQueue.setCc(email.substring(1));

        emailQueue.setSubject(template.getSubject());
        emailQueue.setCode(template.getCode() + evaluation.getChild().getCode() + System.currentTimeMillis());
        emailQueue.setBody(velocityEvaluator.evalute(velocityModel, template.getTemplate()));
        systemService.saveEmailQueue(emailQueue);
    }

}
