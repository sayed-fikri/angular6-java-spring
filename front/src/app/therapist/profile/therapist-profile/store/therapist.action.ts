import {Action} from "@ngrx/store";
import {Therapist, TherapistResult} from "@app/shared/models/identity/therapist.model";
import {ApplicationError} from "@app/shared/models";
import {TherapistProfile} from "@app/therapist/profile/therapist-profile/component/therapist-profile.model";
import {User} from "@app/shared/models/identity/user.model";


export const FIND_THERAPIST = '[Therapist] Find Therapist';
export const FIND_THERAPIST_SUCCESS = '[Therapist] Find Therapist Success';
export const FIND_THERAPIST_ERROR = '[Therapist] Find Therapist Error';

export const FIND_THERAPIST_PROFILE = '[Therapist-Profile] Find Therapist Profile';
export const FIND_THERAPIST_PROFILE_SUCCESS = '[Therapist-Profile] Find Therapist Profile Success';
export const FIND_THERAPIST_PROFILE_ERROR = '[Therapist-Profile] Find Therapist Profile Error';

export const THERAPIST_UPDATE = '[Therapist] Therapist Update';
export const THERAPIST_UPDATE_SUCCESS = '[Therapist] Therapist Update Success';
export const THERAPIST_UPDATE_ERROR = '[Therapist] Therapist Update Error';

export const THERAPIST_USER_UPDATE = '[Therapist : User] Therapist User Update';
export const THERAPIST_USER_UPDATE_SUCCESS = '[Therapist : User] Therapist User Update Success';
export const THERAPIST_USER_UPDATE_ERROR = '[Therapist:User] Therapist User Update Error';



export class FindTherapistAction implements Action{
    readonly type: string = FIND_THERAPIST;

    constructor(){}
}

export class FindTherapistSuccessAction implements  Action{
    readonly type: string = FIND_THERAPIST_SUCCESS;

    constructor(public payload: Therapist ){
    }
}

export class FindTherapistProfileErrorAction implements Action{
    readonly type: string = FIND_THERAPIST_PROFILE_ERROR;

    constructor(public payload: ApplicationError){

    }
}

export class FindTherapistProfileAction implements Action{
    readonly type: string = FIND_THERAPIST_PROFILE;

    constructor(){}
}

export class FindTherapistProfileSuccessAction implements  Action{
    readonly type: string = FIND_THERAPIST_PROFILE_SUCCESS;

    constructor(public payload: TherapistProfile ){
    }
}

export class FindTherapistErrorAction implements Action{
    readonly type: string = FIND_THERAPIST_ERROR;

    constructor(public payload: ApplicationError){

    }
}

export class TherapistUpdateAction implements  Action{
    readonly type: string = THERAPIST_UPDATE;

    constructor(public payload: Therapist){

    }
}

export class TherapistUpdateSuccessAction implements Action{
    readonly type: string = THERAPIST_UPDATE_SUCCESS;

    constructor(public payload: {message: string}){

    }
}

export class TherapistUpdateErrorAction implements Action{
    readonly type: string = THERAPIST_UPDATE_ERROR;

    constructor (public payload : ApplicationError){

    }
}

export class TherapistUserUpdateAction implements  Action{
    readonly type: string = THERAPIST_USER_UPDATE;

    constructor(public payload: User){

    }
}

export class TherapistUserUpdateSuccessAction implements Action{
    readonly type: string = THERAPIST_USER_UPDATE_SUCCESS;

    constructor(public payload: {message: string}){

    }
}

export class TherapistUserUpdateErrorAction implements Action{
    readonly type: string = THERAPIST_USER_UPDATE_ERROR;

    constructor (public payload : ApplicationError){

    }
}
