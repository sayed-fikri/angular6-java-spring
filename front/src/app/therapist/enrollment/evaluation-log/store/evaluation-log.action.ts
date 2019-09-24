import {Action} from '@ngrx/store';
import {ApplicationError} from "@app/shared/models";
import {Consultation} from "@app/shared/models/enrollment/consultation.model";
import {EvaluationLogResult} from "@app/shared/models/enrollment/evaluation-log.model";


export const FIND_EVALUATION_LOGS = '[Evaluation-Log] Find Evaluation-Log';
export const FIND_EVALUATION_LOGS_SUCCESS = '[Evaluation-Log] Find Evaluation-Log Success';
export const FIND_EVALUATION_LOGS_ERROR = '[Evaluation-Log] Find Evaluation-Log Error';


export class FindEvaluationLogAction implements Action {
    readonly type: string = FIND_EVALUATION_LOGS;

    constructor() {
    }
}

export class FindEvaluationLogSuccessAction implements Action {
    readonly type: string = FIND_EVALUATION_LOGS_SUCCESS;

    constructor(public payload: EvaluationLogResult) {
    }
}

export class FindEvaluationLogErrorAction implements Action {
    readonly type: string = FIND_EVALUATION_LOGS_ERROR;

    constructor(public payload: ApplicationError) {
    }
}




