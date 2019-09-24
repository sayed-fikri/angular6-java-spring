import {
    FIND_ALL_CONSULTATIONS_SUCCESS,
    FIND_CONSULTATION_BY_CODE_SUCCESS,
    FIND_PAGED_CONSULTATIONS_SUCCESS, FindAllConsultationsSuccessAction,
    FindConsultationByCodeSuccessAction,
    FindPagedConsultationsSuccessAction,

} from "./consultation.action";
import {
    Consultation,
    consultation,
    ConsultationResult,
    consultationResult
} from "@app/shared/models/enrollment/consultation.model";



export function consultationResultReducer(state = consultationResult, action: FindPagedConsultationsSuccessAction): ConsultationResult {
    switch (action.type) {
        case FIND_PAGED_CONSULTATIONS_SUCCESS:
            return {
                data: action.payload.data,
                totalSize: action.payload.totalSize,
            };
        default: {
            return state;
        }
    }
}

export function consultationsReducer(state = [], action: FindAllConsultationsSuccessAction): Consultation[] {
    switch (action.type) {
        case FIND_ALL_CONSULTATIONS_SUCCESS:
            return [...state, ...action.payload];
        default: {
            return state;
        }
    }
}
export function consultationReducer(state = consultation, action: FindConsultationByCodeSuccessAction): Consultation {
    switch (action.type) {

        case FIND_CONSULTATION_BY_CODE_SUCCESS:
            // console.log(action)

            return {
                ...action.payload
            };
        default: {
            return state;
        }
    }
}

