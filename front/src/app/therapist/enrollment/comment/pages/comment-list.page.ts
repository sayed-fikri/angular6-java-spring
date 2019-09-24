import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {Observable} from "rxjs";
import {FormBuilder} from "@angular/forms";
import {BreadcrumbService} from "@app/component/breadcrumb.service";
import {select, Store} from "@ngrx/store";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {TherapistEnrollmentState} from "../../therapist-enrollment.state";
import {Comment,CommentResult} from "@app/shared/models/enrollment/comment.model";
import {selectComments} from "@app/therapist/enrollment/comment/store/comment.selector";
import {FindPagedCommentsAction} from "@app/therapist/enrollment/comment/store/comment.action";
import {FindConsultationByCodeAction} from "@app/therapist/enrollment/consultation/store/consultation.action";
import {Consultation} from "@app/shared/models/enrollment/consultation.model";
import {selectConsultation} from "@app/therapist/enrollment/consultation/store/consultation.selector";
import {take} from "rxjs/operators";

@Component({
    selector: 'dex-therapist-list-page',
    templateUrl: './comment-list.page.html',
    styleUrls: ['./comment-list.page.css'],
    encapsulation: ViewEncapsulation.None
})
export class CommentListPage implements OnInit {

    consultation$ : Observable<Consultation>;
    comments$ : Observable<Comment[]>;
    commentResult$ : Observable<CommentResult>;

    title = 'Children';
    cols = [
        {field: 'key', header: 'Key'},
        {field: 'value', header: 'Value'},
    ];

    breadcrumbs = [
        {label: 'Enrollment'},
        {label: 'Children '},
        {label: 'Comment '},
    ];

    constructor(public breadcrumbService: BreadcrumbService,
                public messageService : MessageService,
                public fb: FormBuilder,
                public store: Store<TherapistEnrollmentState>,
                public route: ActivatedRoute,
                public router: Router) {
        this.breadcrumbService.setItems(this.breadcrumbs);
        this.comments$ = this.store.pipe(select(selectComments));
        this.consultation$ = this.store.pipe(select(selectConsultation));

        this.route.params.subscribe((params: { code: string, filter:'',page :1})=> {
            this.store.dispatch(
                new FindPagedCommentsAction(params));
        });

        this.route.params.subscribe((params: { code: string})=> {
            this.store.dispatch(
                new FindConsultationByCodeAction(params));
        });    }

    ngOnInit() {

    }

    loadData(event : any) {
        this.consultation$.pipe(take(1)).subscribe(consultation=>{
            let limit = event.rows;
            let offset = event.first;
            let page = (offset / limit) + 1;
            this.store.dispatch(new FindPagedCommentsAction({code: consultation.code,filter:' ',page: page}))
        })

    }


}

