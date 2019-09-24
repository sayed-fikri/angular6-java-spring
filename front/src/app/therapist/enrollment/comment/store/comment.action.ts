import {Action} from '@ngrx/store';
import {ApplicationError} from "@app/shared/models";
import {Child} from "@app/shared/models/enrollment/child.model";
import {Therapist} from "@app/shared/models/identity/therapist.model";
import {Comment,CommentResult} from "@app/shared/models/enrollment/comment.model";


export const FIND_ALL_COMMENTS = '[Comment] Find All Comments';
export const FIND_ALL_COMMENTS_SUCCESS = '[Comment] Find All Comments Success';
export const FIND_ALL_COMMENTS_ERROR = '[Comment] Find All Comments Error';

export const FIND_PAGED_COMMENTS = '[Therapist:Comment] Find Paged Comments';
export const FIND_PAGED_COMMENTS_SUCCESS = '[Therapist:Comment] Find Paged Comments Success';
export const FIND_PAGED_COMMENTS_ERROR = '[Therapist:Comment] Find Paged Comments Error';

export const SAVE_COMMENT = '[Therapist:Comment] Save Comment';
export const SAVE_COMMENT_SUCCESS = '[Therapist:Comment] Save Comment Success';
export const SAVE_COMMENT_ERROR = '[Therapist:Comment] Save Comment Error';

export const UPDATE_COMMENT = '[Guardian:Comment] Update Comment';
export const UPDATE_COMMENT_SUCCESS = '[Guardian:Comment] Update Comment Success';
export const UPDATE_COMMENT_ERROR = '[Guardian:Comment] Update Comment Error';

export const REMOVE_COMMENT = '[Therapist:Comment] Remove Comment';
export const REMOVE_COMMENT_SUCCESS = '[Therapist:Comment] Remove Comment Success';
export const REMOVE_COMMENT_ERROR = '[Therapist:Comment] Remove Comment Error';

export const FIND_COMMENT_BY_CODE = '[Therapist:Comment] Find Comment by code';
export const FIND_COMMENT_BY_CODE_SUCCESS = '[Therapist:Comment] Find Comment by code Success';
export const FIND_COMMENT_BY_CODE_ERROR = '[Therapist:Comment] Find Comment by code Error';;


export class FindAllCommentsAction implements Action {
    readonly type: string = FIND_ALL_COMMENTS;

    constructor() {
    }
}

export class FindAllCommentsSuccessAction implements Action {
    readonly type: string = FIND_ALL_COMMENTS_SUCCESS;

    constructor(public payload: Comment[]) {
    }
}

export class FindAllCommentsErrorAction implements Action {
    readonly type: string = FIND_ALL_COMMENTS_ERROR;

    constructor(public payload: ApplicationError) {
    }
}

export class FindPagedCommentsAction implements Action {
    readonly type: string = FIND_PAGED_COMMENTS;

    constructor(public payload: { code: string,filter: string, page: number }) {
    }
}

export class FindPagedCommentsSuccessAction implements Action {
    readonly type: string = FIND_PAGED_COMMENTS_SUCCESS;

    constructor(public payload: CommentResult) {
    }
}

export class FindPagedCommentsErrorAction implements Action {
    readonly type: string = FIND_PAGED_COMMENTS_ERROR;

    constructor(public payload: ApplicationError) {
    }
}

export class FindCommentByCodeAction implements Action {
    readonly type: string = FIND_COMMENT_BY_CODE;

    constructor(public payload: {code : string}) {
    }
}

export class FindCommentByCodeSuccessAction implements Action {
    readonly type: string = FIND_COMMENT_BY_CODE_SUCCESS;

    constructor(public payload: Comment) {
    }
}

export class FindCommentByCodeErrorAction implements Action {
    readonly type: string = FIND_COMMENT_BY_CODE_ERROR;

    constructor(public payload: ApplicationError) {
    }
}


export class SaveCommentAction implements Action {
    readonly type: string = SAVE_COMMENT;

    constructor(public payload: { code: string, comment: Comment  }) {
    }
}

export class SaveCommentSuccessAction implements Action {
    readonly type: string = SAVE_COMMENT_SUCCESS;

    constructor(public payload: { message: string }) {
    }
}

export class SaveCommentErrorAction implements Action {
    readonly type: string = SAVE_COMMENT_ERROR;

    constructor(public payload: ApplicationError) {
    }
}

export class UpdateCommentAction implements Action {
    readonly type: string = UPDATE_COMMENT;

    constructor(public payload: { code:string,comment: Comment }) {
    }
}

export class UpdateCommentSuccessAction implements Action {
    readonly type: string = UPDATE_COMMENT_SUCCESS;

    constructor(public payload: { message: string }) {
    }
}

export class UpdateCommentErrorAction implements Action {
    readonly type: string = UPDATE_COMMENT_ERROR;

    constructor(public payload: ApplicationError) {
    }
}


export class RemoveCommentAction implements Action {
    readonly type: string = REMOVE_COMMENT;

    constructor(public payload: { code:string,comment: Comment }) {
    }
}

export class RemoveCommentSuccessAction implements Action {
    readonly type: string = REMOVE_COMMENT_SUCCESS;

    constructor(public payload: { message: string }) {
    }
}

export class RemoveCommentErrorAction implements Action {
    readonly type: string = REMOVE_COMMENT_ERROR;

    constructor(public payload: ApplicationError) {
    }
}




