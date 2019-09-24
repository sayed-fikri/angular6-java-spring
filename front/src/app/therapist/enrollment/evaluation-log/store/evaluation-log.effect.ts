import {Injectable} from "@angular/core";
import {Actions, Effect, ofType} from "@ngrx/effects";
import {Action, Store} from "@ngrx/store";
import {Router} from "@angular/router";
import {TherapistEnrollmentState} from "@app/therapist/enrollment/therapist-enrollment.state";
import {TherapistEnrollmentService} from "@app/services/therapist-enrollment.service";
import {Observable, of} from "rxjs";
import {catchError, map, switchMap} from "rxjs/operators";
import {LoadError} from "@app/static/app.action";
import {
    FIND_EVALUATION_LOGS,
    FindEvaluationLogAction, FindEvaluationLogSuccessAction
} from "@app/therapist/enrollment/evaluation-log/store/evaluation-log.action";



@Injectable()
export class EvaluationLogEffects {

    constructor(private actions$: Actions,
                private enrollmentService: TherapistEnrollmentService,
                public store: Store<TherapistEnrollmentState>,
                public router: Router) {
    }

    @Effect()
    public findEvaluationLogs$: Observable<Action> = this.actions$
        .pipe(
            ofType(FIND_EVALUATION_LOGS),
            map((action: FindEvaluationLogAction) => action),
            switchMap(payload => this.enrollmentService.findEvaluationLogs()
                .pipe(
                    map(result => new FindEvaluationLogSuccessAction(result)),
                    catchError(err => of(new LoadError(err)))
                )));


}
