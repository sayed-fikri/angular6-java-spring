import {
    FIND_ALL_COMMENTS_SUCCESS,
    FIND_COMMENT_BY_CODE_SUCCESS,
    FIND_PAGED_COMMENTS_SUCCESS, FindAllCommentsSuccessAction,
    FindCommentByCodeSuccessAction,
    FindPagedCommentsSuccessAction,

} from "./comment.action";
import {
    Comment,
    comment,
    CommentResult,
    commentResult
} from "@app/shared/models/enrollment/comment.model";



export function commentResultReducer(state = commentResult, action: FindPagedCommentsSuccessAction): CommentResult {
    switch (action.type) {
        case FIND_PAGED_COMMENTS_SUCCESS:
            return {
                data: action.payload.data,
                totalSize: action.payload.totalSize,
            };
        default: {
            return state;
        }
    }
}

export function commentsReducer(state = [], action: FindAllCommentsSuccessAction): Comment[] {
    switch (action.type) {
        case FIND_ALL_COMMENTS_SUCCESS:
            return [...state, ...action.payload];
        default: {
            return state;
        }
    }
}
export function commentReducer(state = comment, action: FindCommentByCodeSuccessAction): Comment {
    switch (action.type) {

        case FIND_COMMENT_BY_CODE_SUCCESS:
            // console.log(action)

            return {
                ...action.payload
            };
        default: {
            return state;
        }
    }
}

