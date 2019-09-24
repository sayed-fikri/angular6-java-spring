import {Action} from '@ngrx/store';
import {ApplicationError} from "@app/shared/models";
import {Child, ChildResult} from "@app/shared/models/enrollment/child.model";

export const FIND_PAGED_CHILDREN = '[Therapist:Child] Find Paged Child';
export const FIND_PAGED_CHILDREN_SUCCESS = '[Therapist:Child] Find Paged Child Success';
export const FIND_PAGED_CHILDREN_ERROR = '[Therapist:Child] Find Paged Child Error';

export const FIND_CHILD_BY_CODE = '[Therapist:Child] Find Child by code';
export const FIND_CHILD_BY_CODE_SUCCESS = '[Therapist:Child] Find Child by code Success';
export const FIND_CHILD_BY_CODE_ERROR = '[Therapist:Child] Find Child by code Error';

export class FindPagedChildrenAction implements Action {
    readonly type: string = FIND_PAGED_CHILDREN;

    constructor(public payload: {page: number }) {
    }
}

export class FindPagedChildrenSuccessAction implements Action {
    readonly type: string = FIND_PAGED_CHILDREN_SUCCESS;

    constructor(public payload: ChildResult) {
    }
}

export class FindPagedChildrenErrorAction implements Action {
    readonly type: string = FIND_PAGED_CHILDREN_ERROR;

    constructor(public payload: ApplicationError) {
    }
}

export class FindChildByCodeAction implements Action {
    readonly type: string = FIND_CHILD_BY_CODE;

    constructor(public payload: { code: string }) {
    }
}

export class FindChildByCodeSuccessAction implements Action {
    readonly type: string = FIND_CHILD_BY_CODE_SUCCESS;

    constructor(public payload: Child) {
    }
}

export class FindChildByCodeErrorAction implements Action {
    readonly type: string = FIND_CHILD_BY_CODE_ERROR;

    constructor(public payload: ApplicationError) {
    }
}
