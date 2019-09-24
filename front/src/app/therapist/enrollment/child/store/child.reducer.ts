import {
    FIND_CHILD_BY_CODE_SUCCESS,
    FIND_PAGED_CHILDREN_SUCCESS,
    FindChildByCodeSuccessAction,
    FindPagedChildrenSuccessAction
} from "./child.action";
import {Child, child, childResult, ChildResult} from "@app/shared/models/enrollment/child.model";

export function childResultReducer(state = childResult, action: FindPagedChildrenSuccessAction): ChildResult {
    switch (action.type) {
        case FIND_PAGED_CHILDREN_SUCCESS:
            return {
                data: action.payload.data,
                totalSize: action.payload.totalSize,
            };
        default: {
            return state;
        }
    }
}

export function childReducer(state = child, action: FindChildByCodeSuccessAction): Child {
    switch (action.type) {

        case FIND_CHILD_BY_CODE_SUCCESS:
            return {
                ...action.payload
            };
        default: {
            return state;
        }
    }
}
