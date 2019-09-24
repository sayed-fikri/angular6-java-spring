import {Actions, Effect, ofType} from "@ngrx/effects";
import {Injectable} from "@angular/core";
import {Observable, of} from "rxjs";
import {Action} from "@ngrx/store";


import {catchError, map, switchMap} from "rxjs/operators";
import {LoadError} from "@app/static/app.action";
import {
    FIND_CHILD_BY_CODE,
    FIND_PAGED_CHILDREN,
    FindChildByCodeAction,
    FindChildByCodeSuccessAction,
    FindPagedChildrenAction,
    FindPagedChildrenSuccessAction,
} from "./child.action";
import {ActivatedRoute, Router} from "@angular/router";
import {TherapistEnrollmentService} from "@app/services/therapist-enrollment.service";


@Injectable()
export class ChildEffects {

    constructor(private actions$: Actions,
                private router: Router,
                private route: ActivatedRoute,
                private enrollmentService: TherapistEnrollmentService) {
    }

    @Effect()
    public findPagedChildren$: Observable<Action> = this.actions$
        .pipe(
            ofType(FIND_PAGED_CHILDREN),
            map((action: FindPagedChildrenAction) => action.payload),
            switchMap(payload => this.enrollmentService.findPagedChildren(payload.page)
                .pipe(
                    map(result => new FindPagedChildrenSuccessAction(result)),
                    catchError(err => of(new LoadError(err)))
                )));

    @Effect()
    public findChildByCode: Observable<Action> = this.actions$
        .pipe(
            ofType(FIND_CHILD_BY_CODE),
            map((action: FindChildByCodeAction) => action.payload),
            switchMap(payload => this.enrollmentService.findChildByCode(payload.code)),
            map(result => new FindChildByCodeSuccessAction(result)),);
}
