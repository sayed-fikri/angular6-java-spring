import {Injectable} from "@angular/core";
import {Actions, Effect, ofType} from "@ngrx/effects";
import {Observable, of} from "rxjs";
import {Action, Store} from "@ngrx/store";
import {catchError, map, mergeMap, switchMap, tap} from "rxjs/operators";
import {
    FIND_CONSULTATION_BY_CHILD,
    FIND_CONSULTATION_BY_CODE,
    FIND_PAGED_CONSULTATIONS, FindConsultationByChildAction,
    FindConsultationByCodeAction,
    FindConsultationByCodeSuccessAction,
    FindPagedConsultationsAction,
    FindPagedConsultationsSuccessAction,
    REMOVE_CONSULTATION,
    RemoveConsultationAction,
    RemoveConsultationSuccessAction,
    SAVE_CONSULTATION,
    SaveConsultationAction,
    SaveConsultationSuccessAction,
    UPDATE_CONSULTATION,
    UpdateConsultationAction,
    UpdateConsultationSuccessAction,
} from "./consultation.action";
import {Router} from "@angular/router";
import {LoadError} from "@app/static/app.action";
import {ToastSuccessAction} from "@app/shared/store/toast.action";
import {TherapistEnrollmentState} from "@app/therapist/enrollment/therapist-enrollment.state";
import {TherapistEnrollmentService} from "@app/services/therapist-enrollment.service";



@Injectable()
export class ConsultationEffects {

    constructor(private actions$: Actions,
                private enrollmentService: TherapistEnrollmentService,
                public store: Store<TherapistEnrollmentState>,
                public router: Router) {
    }

    @Effect()
    public findPagedConsultations$: Observable<Action> = this.actions$
        .pipe(
            ofType(FIND_PAGED_CONSULTATIONS),
            map((action: FindPagedConsultationsAction) => action.payload),
            switchMap(payload => this.enrollmentService.findPagedConsultations(payload.filter, payload.page).pipe(
                map(result => new FindPagedConsultationsSuccessAction(result)),
                catchError(err => of(new LoadError(err)))
            )));


    @Effect()
    public findConsultationByCode: Observable<Action> = this.actions$
        .pipe(
            ofType(
                FIND_CONSULTATION_BY_CODE),
            map((action: FindConsultationByCodeAction) => action.payload),
            switchMap(payload => this.enrollmentService.findConsultationByCode(payload.code)),
            map(result => new FindConsultationByCodeSuccessAction(result)),);

    @Effect()
    public findConsultationByChild: Observable<Action> = this.actions$
        .pipe(
            ofType(
                FIND_CONSULTATION_BY_CHILD),
            map((action: FindConsultationByChildAction) => action.payload),
            switchMap(payload => this.enrollmentService.findConsultationByChild(payload.code)),
            map(result => new FindConsultationByCodeSuccessAction(result)),);

    @Effect() saveChild$ = this.actions$
        .pipe(
            ofType(SAVE_CONSULTATION),
            map((action: SaveConsultationAction) => action.payload),
            switchMap((payload) => this.enrollmentService.saveConsultation(payload.child,payload.therapist).pipe(
                mergeMap(result => [
                    new SaveConsultationSuccessAction({message: ''}),
                    new ToastSuccessAction({
                        key: 'globalToast',
                        life: 5000,
                        severity: 'success',
                        summary: 'Success!!!',
                        detail: 'New therapist added'
                    }),
                ]),
                tap((action) => {
                    this.router.navigate(['/guardian/enrollment/children-profile/', payload.child.code])
                }),
                catchError(err => of(new LoadError(err))),
            )),);

    @Effect() updateConsultation$ = this.actions$
        .pipe(
            ofType(UPDATE_CONSULTATION),
            map((action: UpdateConsultationAction) => action.payload),
            switchMap((payload) => this.enrollmentService.updateConsultation(payload.code,payload.consultation).pipe(
                mergeMap(_ => [
                    new UpdateConsultationSuccessAction({message: ''}),
                    new ToastSuccessAction({
                        key: 'globalToast',
                        life: 5000,
                        severity: 'success',
                        summary: 'Comment saved',
                    }),
                ]),
                tap((action) => {
                    this.router.navigate(['/therapist/enrollment/children-list'])
                }),
                catchError(err => of(new LoadError(err))),
            )),
        );

    @Effect() removeConsultation$ = this.actions$
        .pipe(
            ofType(REMOVE_CONSULTATION),
            map((action: RemoveConsultationAction) => action.payload),
            switchMap(payload => this.enrollmentService.removeConsultation(payload.consultation).pipe(
                map(message => new RemoveConsultationSuccessAction({message: 'success'})),
                catchError(err => of(new LoadError(err)))
            )),);

}
