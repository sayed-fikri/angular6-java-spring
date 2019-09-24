import {createSelector} from "@ngrx/store";
import {selectTherapistEnrollmentState} from "@app/therapist/enrollment/therapist-enrollment.state";

export const selectConsultationResultState = createSelector(selectTherapistEnrollmentState, state => state.consultationResult);
export const selectConsultations = createSelector(selectConsultationResultState, state => state.data);
export const selectConsultationTotalSize = createSelector(selectConsultationResultState, state => state.totalSize);
export const selectAllConsultations = createSelector(selectTherapistEnrollmentState, state => state.consultations);
export const selectConsultation = createSelector(selectTherapistEnrollmentState, state => state.consultation);

