import {Child, ChildResult} from "@app/shared/models/enrollment/child.model";
import {childReducer, childResultReducer} from "./child/store/child.reducer";
import {ActionReducerMap, createFeatureSelector} from "@ngrx/store";
import {AppState} from "@app/core/core.state";
import {Consultation, ConsultationResult} from "@app/shared/models/enrollment/consultation.model";
import {
    consultationReducer,
    consultationResultReducer,
    consultationsReducer
} from "@app/therapist/enrollment/consultation/store/consultation.reducer";
import {Comment,CommentResult} from "@app/shared/models/enrollment/comment.model";
import {
    commentReducer,
    commentResultReducer,
    commentsReducer
} from "@app/therapist/enrollment/comment/store/comment.reducer";
import { EvaluationLogResult} from "@app/shared/models/enrollment/evaluation-log.model";
import {evaluationLogResultReducer} from "@app/therapist/enrollment/evaluation-log/store/evaluation-log.reducer";

export const FEATURE_NAME = 'therapistEnrollment';
export const selectTherapistEnrollmentState = createFeatureSelector<State, TherapistEnrollmentState>(
    FEATURE_NAME
);

export const reducers: ActionReducerMap<TherapistEnrollmentState> = {
    childResult: childResultReducer,
    child: childReducer,
    consultationResult : consultationResultReducer,
    consultations :consultationsReducer,
    consultation : consultationReducer,
    commentResult:commentResultReducer,
    comments:commentsReducer,
    comment : commentReducer,
    evaluationLogResult : evaluationLogResultReducer

};

export interface TherapistEnrollmentState {
    childResult: ChildResult;
    child: Child;
    consultationResult : ConsultationResult;
    consultations : Consultation[];
    consultation : Consultation;
    commentResult : CommentResult;
    comments : Comment[];
    comment : Comment;
    evaluationLogResult : EvaluationLogResult;

}

export interface State extends AppState {
    therapistEnrollment: TherapistEnrollmentState;
}
