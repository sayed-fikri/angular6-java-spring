import {ActionReducerMap, createFeatureSelector} from "@ngrx/store";
import {AppState} from "@app/core/core.state";
import {
    therapistProfileReducer, therapistReducer,
} from "@app/therapist/profile/therapist-profile/store/therapist.reducer";
import {Therapist} from "@app/shared/models/identity/therapist.model";
import {TherapistProfile} from "@app/therapist/profile/therapist-profile/component/therapist-profile.model";



export const FEATURE_NAME = 'therapistProfile';
export const selectTherapistProfileState = createFeatureSelector<State, TherapistProfileState>(
    FEATURE_NAME
);

export const reducers: ActionReducerMap<TherapistProfileState> = {

    therapist: therapistReducer,
    therapistProfile: therapistProfileReducer,
    // therapistProfiles: therapistProfilesReducer,
    // therapistProfile: therapistProfileReducer,


};

export interface TherapistProfileState {

    therapist: Therapist;
    therapistProfile : TherapistProfile;
    // therapistProfileResult: TherapistProfileResult;
    // therapistProfiles:  TherapistProfile[];
    // therapistProfile:  TherapistProfile;

}

export interface State extends AppState {
    therapistProfile: TherapistProfileState;
}
