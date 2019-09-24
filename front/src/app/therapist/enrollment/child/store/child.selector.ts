import {createSelector} from '@ngrx/store';
import {selectTherapistEnrollmentState} from "../../therapist-enrollment.state";

export const selectChildResultState = createSelector(selectTherapistEnrollmentState, state => state.childResult);
export const selectChildren = createSelector(selectChildResultState, state => state.data);
export const selectChildrenTotalSize = createSelector(selectChildResultState, state => state.totalSize);
export const selectChild = createSelector(selectTherapistEnrollmentState, state => state.child);
