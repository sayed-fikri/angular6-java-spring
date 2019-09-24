import {initStateTherapist, Therapist} from "@app/shared/models/identity/therapist.model";
import {initStateUser, User} from "@app/shared/models/identity/user.model";

export interface TherapistProfile{
    therapist: Therapist;
    user : User;
}

export const initStateTherapistProfile: TherapistProfile = {
    therapist : initStateTherapist,
    user:initStateUser,
};


