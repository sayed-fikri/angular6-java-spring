

import {EvaluationLogResult, evaluationLogResult} from "@app/shared/models/enrollment/evaluation-log.model";
import {
    FIND_EVALUATION_LOGS_SUCCESS,
    FindEvaluationLogSuccessAction
} from "@app/therapist/enrollment/evaluation-log/store/evaluation-log.action";



export function evaluationLogResultReducer(state = evaluationLogResult, action: FindEvaluationLogSuccessAction): EvaluationLogResult {
    switch (action.type) {
        case FIND_EVALUATION_LOGS_SUCCESS:
            return {
                data: action.payload.data,
                totalSize: action.payload.totalSize,
            };
        default: {
            return state;
        }
    }
}



