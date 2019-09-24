import {createSelector} from "@ngrx/store";
import {selectTherapistEnrollmentState} from "@app/therapist/enrollment/therapist-enrollment.state";

export const selectCommentResultState = createSelector(selectTherapistEnrollmentState, state => state.commentResult);
export const selectComments = createSelector(selectCommentResultState, state => state.data);
export const selectCommentTotalSize = createSelector(selectCommentResultState, state => state.totalSize);
export const selectAllComments = createSelector(selectTherapistEnrollmentState, state => state.comments);
export const selectComment = createSelector(selectTherapistEnrollmentState, state => state.comment);

