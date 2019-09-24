import {Injectable} from "@angular/core";
import {Actions, Effect, ofType} from "@ngrx/effects";
import {Observable, of} from "rxjs";
import {Action, Store} from "@ngrx/store";
import {catchError, map, mergeMap, switchMap, tap} from "rxjs/operators";
import {
    FIND_COMMENT_BY_CODE,
    FIND_PAGED_COMMENTS,
    FindCommentByCodeAction,
    FindCommentByCodeSuccessAction,
    FindPagedCommentsAction,
    FindPagedCommentsSuccessAction,
    REMOVE_COMMENT,
    RemoveCommentAction,
    RemoveCommentSuccessAction,
    SAVE_COMMENT,
    SaveCommentAction,
    SaveCommentSuccessAction,
    UPDATE_COMMENT,
    UpdateCommentAction,
    UpdateCommentSuccessAction,
} from "./comment.action";
import {Router} from "@angular/router";
import {LoadError} from "@app/static/app.action";
import {ToastSuccessAction} from "@app/shared/store/toast.action";
import {TherapistEnrollmentState} from "@app/therapist/enrollment/therapist-enrollment.state";
import {TherapistEnrollmentService} from "@app/services/therapist-enrollment.service";
import {FindPagedGuardiansAction} from "@app/staff/identity/guardians/store/guardian.action";



@Injectable()
export class CommentEffects {

    constructor(private actions$: Actions,
                private enrollmentService: TherapistEnrollmentService,
                public store: Store<TherapistEnrollmentState>,
                public router: Router) {
    }

    @Effect()
    public findPagedComments$: Observable<Action> = this.actions$
        .pipe(
            ofType(FIND_PAGED_COMMENTS),
            map((action: FindPagedCommentsAction) => action.payload),
            switchMap(payload => this.enrollmentService.findPagedComments(payload.code,payload.filter, payload.page).pipe(
                map(result => new FindPagedCommentsSuccessAction(result)),
                catchError(err => of(new LoadError(err)))
            )));


    @Effect()
    public findCommentByCode: Observable<Action> = this.actions$
        .pipe(
            ofType(
                FIND_COMMENT_BY_CODE),
            map((action: FindCommentByCodeAction) => action.payload),
            switchMap(payload => this.enrollmentService.findCommentByCode(payload.code)),
            map(result => new FindCommentByCodeSuccessAction(result)),);

    @Effect() saveComment$ = this.actions$
        .pipe(
            ofType(SAVE_COMMENT),
            map((action: SaveCommentAction) => action.payload),
            switchMap((payload) => this.enrollmentService.saveComment(payload.code,payload.comment).pipe(
                mergeMap(result => [
                    new SaveCommentSuccessAction({message: ''}),
                    new ToastSuccessAction({
                        key: 'globalToast',
                        life: 5000,
                        severity: 'success',
                        summary: 'Success!!!',
                        detail: 'New comment added'
                    }),
                    new FindPagedCommentsAction({code:payload.code,filter: '', page: 1})
                ]),
                catchError(err => of(new LoadError(err))),
            )),);

    @Effect() updateComment$ = this.actions$
        .pipe(
            ofType(UPDATE_COMMENT),
            map((action: UpdateCommentAction) => action.payload),
            switchMap((payload) => this.enrollmentService.updateComment(payload.code,payload.comment).pipe(
                mergeMap(result => [
                    new UpdateCommentSuccessAction({message: ''}),
                    new ToastSuccessAction({
                        key: 'globalToast',
                        life: 5000,
                        severity: 'success',
                        summary: 'Success',
                        detail: 'Comment updated'
                    }),
                    new FindPagedCommentsAction({code:payload.code,filter: '', page: 1})
                ]),
                catchError(err => of(new LoadError(err))),
            )),);

    @Effect() removeComment$ = this.actions$
        .pipe(
            ofType(REMOVE_COMMENT),
            map((action: RemoveCommentAction) => action.payload),
            switchMap(payload => this.enrollmentService.removeComment(payload.code,payload.comment).pipe(
                mergeMap(result => [
                    new SaveCommentSuccessAction({message: ''}),
                    new ToastSuccessAction({
                        key: 'globalToast',
                        life: 5000,
                        severity: 'success',
                        summary: 'Success!!!',
                        detail: 'Comment deleted'
                    }),
                    new FindPagedCommentsAction({code:payload.code,filter: '', page: 1})
                ]),
                catchError(err => of(new LoadError(err))),
            )),);

}
