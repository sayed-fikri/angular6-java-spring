import {ActivatedRoute, Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {Actions, Effect, ofType} from "@ngrx/effects";
import {TherapistIdentityService} from "@app/services/therapist-identity.service";
import {
    FIND_THERAPIST, FIND_THERAPIST_PROFILE,
    FindTherapistAction,
    FindTherapistProfileAction,
    FindTherapistProfileSuccessAction,
    FindTherapistSuccessAction,
    THERAPIST_UPDATE, THERAPIST_USER_UPDATE,
    TherapistUpdateAction,
    TherapistUpdateSuccessAction, TherapistUserUpdateAction
} from "@app/therapist/profile/therapist-profile/store/therapist.action";
import {Action} from "@ngrx/store";
import {from, Observable, of} from "rxjs";
import {catchError, map, mergeMap, switchMap, tap} from "rxjs/operators";
import {LoadError} from "@app/static/app.action";
import {ToastSuccessAction} from "@app/shared/store/toast.action";


@Injectable()
export class TherapistEffect {

    constructor(private actions$: Actions,
                private router: Router,
                private route: ActivatedRoute,
                private identityService: TherapistIdentityService) {

    }

    @Effect()
    public findTherapist: Observable<Action> = this.actions$
        .pipe(
            ofType(FIND_THERAPIST),
            map((action: FindTherapistAction) => action),
            switchMap(payload => this.identityService.findTherapist()),
            map(result => new FindTherapistSuccessAction(result)),);

    @Effect()
    public findTherapistProfile: Observable<Action> = this.actions$
        .pipe(
            ofType(FIND_THERAPIST_PROFILE),
            map((action: FindTherapistProfileAction) => action),
            switchMap(payload => this.identityService.findTherapistProfile()),
            map(result => new FindTherapistProfileSuccessAction(result)),);


    @Effect() therapistUpdate$ = this.actions$
        .pipe(
            ofType(THERAPIST_UPDATE),
            map((action: TherapistUpdateAction) => action.payload),
            switchMap((therapist) => this.identityService.therapistUpdate(therapist).pipe(
                mergeMap(result => [
                    new TherapistUpdateSuccessAction({message: ''}),
                    new ToastSuccessAction({
                        key: 'globalToast',
                        life: 5000,
                        severity: 'success',
                        summary: 'Update success',
                        detail: 'Your profile has successfully updated'
                    }),
                ]),
                tap((action) => {
                    this.router.navigate(['/therapist/therapist-profile'])
                }),
                catchError(err => of(new LoadError(err))),
            )),);


    @Effect() therapistUserUpdate$ = this.actions$
        .pipe(
            ofType(THERAPIST_USER_UPDATE),
            map((action: TherapistUserUpdateAction) => action.payload),
            switchMap((user) => this.identityService.therapistUserUpdate(user).pipe(
                mergeMap(result => [
                    new TherapistUpdateSuccessAction({message: ''}),
                    new ToastSuccessAction({
                        key: 'globalToast',
                        life: 5000,
                        severity: 'success',
                        summary: 'Success',
                        detail: 'Your password has successfully changed'
                    }),
                ]),
                tap((action) => {
                    this.router.navigate(['/therapist/therapist-profile'])
                }),
                catchError(err => of(new LoadError(err))),
            )),);

}
