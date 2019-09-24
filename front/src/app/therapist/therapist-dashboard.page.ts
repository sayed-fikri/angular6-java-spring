import {Component, OnInit} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {BreadcrumbService} from "../component/breadcrumb.service";
import {EnrollmentResult} from "@app/shared/models/enrollment/enrollment.model";
import {ActivatedRoute, Router} from "@angular/router";
import {select, Store} from "@ngrx/store";
import {StaffLearningState} from "@app/staff/learning/staff-learning.state";
import {selectEnrollmentResultState} from "@app/guardian/enrollment/enrollments/store/enrollment.selector";
import {ChildResult} from "@app/shared/models/enrollment/child.model";
import {selectChildResultState} from "@app/guardian/enrollment/child/store/child.selector";
import {Consultation, ConsultationResult} from "@app/shared/models/enrollment/consultation.model";
import {FindPagedConsultationsAction} from "@app/therapist/enrollment/consultation/store/consultation.action";
import {selectConsultationResultState} from "@app/therapist/enrollment/consultation/store/consultation.selector";
import {FindEvaluationLogAction} from "@app/therapist/enrollment/evaluation-log/store/evaluation-log.action";
import {EvaluationLogResult} from "@app/shared/models/enrollment/evaluation-log.model";
import {selectEvaluationLogResultState} from "@app/therapist/enrollment/evaluation-log/store/evaluation-log.selector";

@Component({
    selector: 'dex-therapist-dashboard-page',
    templateUrl: './therapist-dashboard.page.html',
    styleUrls: ['./therapist-dashboard.page.css']
})
export class TherapistDashboard implements OnInit {

    title1 = 'Welcome To Irichment';

    children$: Observable<ChildResult>;
    enrollment$: Observable<EnrollmentResult>;
    consultationResult$ : Observable<ConsultationResult>;
    evaluationLog$ : Observable<EvaluationLogResult>;
    title = 'Enrollment List';
    cols = [
        {field: 'key', header: 'Key'},
        {field: 'value', header: 'Value'},
    ];
    breadcrumbs: any [] = [
        {label: 'Dashboard'},
    ];



    constructor(public http: HttpClient,
                public breadcrumbService: BreadcrumbService,
                public route: ActivatedRoute,
                public router: Router,
                public store: Store<StaffLearningState>) {
        this.breadcrumbService.setItems(this.breadcrumbs);
        this.children$ = this.store.pipe(select(selectChildResultState ));
        this.enrollment$ = this.store.pipe(select(selectEnrollmentResultState));
        this.consultationResult$ = this.store.pipe(select(selectConsultationResultState));
        this.evaluationLog$ = this.store.pipe(select(selectEvaluationLogResultState));
    }


    ngOnInit(): void {

        this.store.dispatch(new FindPagedConsultationsAction({filter:' ',page: 1}));
        this.store.dispatch(new FindEvaluationLogAction());

    }

    childProfile(consultation: Consultation): void {
        this.router.navigate(['/therapist/enrollment/children-profile',consultation.child.code]);
    }


}
