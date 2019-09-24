import {Action} from '@ngrx/store';
import {ApplicationError} from "@app/shared/models";
import {Child} from "@app/shared/models/enrollment/child.model";
import {Consultation, ConsultationResult} from "@app/shared/models/enrollment/consultation.model";
import {Therapist} from "@app/shared/models/identity/therapist.model";


export const FIND_ALL_CONSULTATIONS = '[Consultation] Find All Consultations';
export const FIND_ALL_CONSULTATIONS_SUCCESS = '[Consultation] Find All Consultations Success';
export const FIND_ALL_CONSULTATIONS_ERROR = '[Consultation] Find All Consultations Error';

export const FIND_PAGED_CONSULTATIONS = '[Therapist:Consultation] Find Paged Consultations';
export const FIND_PAGED_CONSULTATIONS_SUCCESS = '[Therapist:Consultation] Find Paged Consultations Success';
export const FIND_PAGED_CONSULTATIONS_ERROR = '[Therapist:Consultation] Find Paged Consultations Error';

export const SAVE_CONSULTATION = '[Therapist:Consultation] Save Consultation';
export const SAVE_CONSULTATION_SUCCESS = '[Therapist:Consultation] Save Consultation Success';
export const SAVE_CONSULTATION_ERROR = '[Therapist:Consultation] Save Consultation Error';

export const UPDATE_CONSULTATION = '[Guardian:Consultation] Update Consultation';
export const UPDATE_CONSULTATION_SUCCESS = '[Guardian:Consultation] Update Consultation Success';
export const UPDATE_CONSULTATION_ERROR = '[Guardian:Consultation] Update Consultation Error';

export const REMOVE_CONSULTATION = '[Therapist:Consultation] Remove Consultation';
export const REMOVE_CONSULTATION_SUCCESS = '[Therapist:Consultation] Remove Consultation Success';
export const REMOVE_CONSULTATION_ERROR = '[Therapist:Consultation] Remove Consultation Error';

export const FIND_CONSULTATION_BY_CODE = '[Therapist:Consultation] Find Consultation by code';
export const FIND_CONSULTATION_BY_CODE_SUCCESS = '[Therapist:Consultation] Find Consultation by code Success';
export const FIND_CONSULTATION_BY_CODE_ERROR = '[Therapist:Consultation] Find Consultation by code Error';

export const FIND_CONSULTATION_BY_CHILD = '[Therapist:Consultation] Find Consultation by child';
export const FIND_CONSULTATION_BY_CHILD_SUCCESS = '[Therapist:Consultation] Find Consultation by child Success';
export const FIND_CONSULTATION_BY_CHILD_ERROR = '[Therapist:Consultation] Find Consultation by child Error';


export class FindAllConsultationsAction implements Action {
    readonly type: string = FIND_ALL_CONSULTATIONS;

    constructor() {
    }
}

export class FindAllConsultationsSuccessAction implements Action {
    readonly type: string = FIND_ALL_CONSULTATIONS_SUCCESS;

    constructor(public payload: Consultation[]) {
    }
}

export class FindAllConsultationsErrorAction implements Action {
    readonly type: string = FIND_ALL_CONSULTATIONS_ERROR;

    constructor(public payload: ApplicationError) {
    }
}

export class FindPagedConsultationsAction implements Action {
    readonly type: string = FIND_PAGED_CONSULTATIONS;

    constructor(public payload: { filter: string, page: number }) {
    }
}

export class FindPagedConsultationsSuccessAction implements Action {
    readonly type: string = FIND_PAGED_CONSULTATIONS_SUCCESS;

    constructor(public payload: ConsultationResult) {
    }
}

export class FindPagedConsultationsErrorAction implements Action {
    readonly type: string = FIND_PAGED_CONSULTATIONS_ERROR;

    constructor(public payload: ApplicationError) {
    }
}

export class FindConsultationByCodeAction implements Action {
    readonly type: string = FIND_CONSULTATION_BY_CODE;

    constructor(public payload: {code : string}) {
    }
}

export class FindConsultationByCodeSuccessAction implements Action {
    readonly type: string = FIND_CONSULTATION_BY_CODE_SUCCESS;

    constructor(public payload: Consultation) {
    }
}

export class FindConsultationByCodeErrorAction implements Action {
    readonly type: string = FIND_CONSULTATION_BY_CODE_ERROR;

    constructor(public payload: ApplicationError) {
    }
}


export class FindConsultationByChildAction implements Action {
    readonly type: string = FIND_CONSULTATION_BY_CHILD;

    constructor(public payload: {code : string}) {
    }
}

export class FindConsultationByChildSuccessAction implements Action {
    readonly type: string = FIND_CONSULTATION_BY_CHILD_SUCCESS;

    constructor(public payload: Consultation) {
    }
}

export class FindConsultationByChildErrorAction implements Action {
    readonly type: string = FIND_CONSULTATION_BY_CHILD_ERROR;

    constructor(public payload: ApplicationError) {
    }
}


export class SaveConsultationAction implements Action {
    readonly type: string = SAVE_CONSULTATION;

    constructor(public payload: { child: Child, therapist: Therapist  }) {
    }
}

export class SaveConsultationSuccessAction implements Action {
    readonly type: string = SAVE_CONSULTATION_SUCCESS;

    constructor(public payload: { message: string }) {
    }
}

export class SaveConsultationErrorAction implements Action {
    readonly type: string = SAVE_CONSULTATION_ERROR;

    constructor(public payload: ApplicationError) {
    }
}

export class UpdateConsultationAction implements Action {
    readonly type: string = UPDATE_CONSULTATION;

    constructor(public payload: { code:string, consultation: Consultation }) {
    }
}

export class UpdateConsultationSuccessAction implements Action {
    readonly type: string = UPDATE_CONSULTATION_SUCCESS;

    constructor(public payload: { message: string }) {
    }
}

export class UpdateConsultationErrorAction implements Action {
    readonly type: string = UPDATE_CONSULTATION_ERROR;

    constructor(public payload: ApplicationError) {
    }
}


export class RemoveConsultationAction implements Action {
    readonly type: string = REMOVE_CONSULTATION;

    constructor(public payload: { consultation: Consultation }) {
    }
}

export class RemoveConsultationSuccessAction implements Action {
    readonly type: string = REMOVE_CONSULTATION_SUCCESS;

    constructor(public payload: { message: string }) {
    }
}

export class RemoveConsultationErrorAction implements Action {
    readonly type: string = REMOVE_CONSULTATION_ERROR;

    constructor(public payload: ApplicationError) {
    }
}




