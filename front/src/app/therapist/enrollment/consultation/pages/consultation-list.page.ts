import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {Observable} from "rxjs";
import {FormBuilder} from "@angular/forms";
import {BreadcrumbService} from "@app/component/breadcrumb.service";
import {select, Store} from "@ngrx/store";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {TherapistEnrollmentState} from "../../therapist-enrollment.state";
import {Consultation, ConsultationResult} from "@app/shared/models/enrollment/consultation.model";
import {FindPagedConsultationsAction} from "@app/therapist/enrollment/consultation/store/consultation.action";
import {
    selectConsultationResultState,
    selectConsultations
} from "@app/therapist/enrollment/consultation/store/consultation.selector";

@Component({
    selector: 'dex-therapist-list-page',
    templateUrl: './consultation-list.page.html',
    styleUrls: ['./consultation-list.page.css'],
    encapsulation: ViewEncapsulation.None
})
export class ConsultationListPage implements OnInit {

    consultations$ : Observable<Consultation[]>;
    consultationResult$ : Observable<ConsultationResult>;

    title = 'My Patient';
    cols = [
        {field: 'key', header: 'Key'},
        {field: 'value', header: 'Value'},
    ];

    breadcrumbs = [
        {label: 'Enrollment'},
        {label: 'Children ', routerLink: ['/therapist/enrollment/children']}
    ];

    constructor(public breadcrumbService: BreadcrumbService,
                public messageService : MessageService,
                public fb: FormBuilder,
                public store: Store<TherapistEnrollmentState>,
                public route: ActivatedRoute,
                public router: Router) {
        this.breadcrumbService.setItems(this.breadcrumbs);
        this.consultations$ = this.store.pipe(select(selectConsultations));
        this.consultationResult$ = this.store.pipe(select(selectConsultationResultState));
    }

    ngOnInit() {

    }

    loadData(event : any) {
        let limit = event.rows;
        let offset = event.first;
        let page = (offset / limit) + 1;
        this.store.dispatch(new FindPagedConsultationsAction({filter:' ',page: page}))
    }

    childProfile(consultation: Consultation): void {
        this.router.navigate(['/therapist/enrollment/children-profile',consultation.child.code]);
    }


}

