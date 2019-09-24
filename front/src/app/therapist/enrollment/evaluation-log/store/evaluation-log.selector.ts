import {createSelector} from "@ngrx/store";
import {selectTherapistEnrollmentState} from "@app/therapist/enrollment/therapist-enrollment.state";

export const selectEvaluationLogResultState = createSelector(selectTherapistEnrollmentState, state => state.evaluationLogResult);
export const selectEvaluationLogs = createSelector(selectEvaluationLogResultState, state => state.data);


