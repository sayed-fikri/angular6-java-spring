import {
    FIND_THERAPIST_PROFILE_SUCCESS,
    FIND_THERAPIST_SUCCESS, FindTherapistProfileSuccessAction,
    FindTherapistSuccessAction
} from "@app/therapist/profile/therapist-profile/store/therapist.action";
import {initStateTherapist, Therapist, TherapistResult} from "@app/shared/models/identity/therapist.model";
import {
    initStateTherapistProfile,
    TherapistProfile
} from "@app/therapist/profile/therapist-profile/component/therapist-profile.model";


export function therapistReducer(state = initStateTherapist, action: FindTherapistSuccessAction): Therapist{
    switch (action.type){
        case FIND_THERAPIST_SUCCESS:
            return{
                ...action.payload
            };
        default:{
            return state;
        }
    }
}

export function therapistProfileReducer(state = initStateTherapistProfile, action: FindTherapistProfileSuccessAction): TherapistProfile{
    switch (action.type){
        case FIND_THERAPIST_PROFILE_SUCCESS:
            return{
                ...action.payload
            };
        default:{
            return state;
        }
    }
}
