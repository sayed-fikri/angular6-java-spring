import {createSelector} from "@ngrx/store";
import {selectTherapistProfileState} from "@app/therapist/profile/therapist-profile/store/therapist-profile.state";

export const selectTherapist = createSelector(selectTherapistProfileState, state => state.therapist);
export const selectTherapistProfile = createSelector(selectTherapistProfileState, state => state.therapistProfile);
