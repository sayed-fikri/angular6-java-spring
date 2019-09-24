import {AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {BreadcrumbService} from "@app/component/breadcrumb.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {child, Child} from "@app/shared/models/enrollment/child.model";
import {ConfirmationService} from "primeng/api";
import {select, Store} from "@ngrx/store";
import {FindChildByCodeAction} from "@app/guardian/enrollment/child/store/child.action";
import {selectChild} from "@app/guardian/enrollment/child/store/child.selector";
import {UIChart} from "primeng/chart";
import {
    selectCalibration,
    selectSectionResponses
} from "@app/guardian/enrollment/calibration/store/calibration.selector";
import {filter, take} from "rxjs/operators";
import {CalibrationSectionResponse} from "@app/shared/models/enrollment/calibration-section-response.model";
import {untilDestroyed} from "ngx-take-until-destroy";
import {
    FindCalibrationByChildAction,
} from "@app/guardian/enrollment/calibration/store/calibration.action";
import {Calibration} from "@app/shared/models/enrollment/calibration.model";
import {GuardianEnrollmentState} from "@app/guardian/enrollment/guardian-enrollment.state";
import {FindEnrollmentByChildAction} from "@app/guardian/enrollment/enrollments/store/enrollment.action";
import {Enrollment} from "@app/shared/models/enrollment/enrollment.model";
import {selectEnrollment} from "@app/guardian/enrollment/enrollments/store/enrollment.selector";
import {Therapist} from "@app/shared/models/identity/therapist.model";
import {Consultation, ConsultationResult} from "@app/shared/models/enrollment/consultation.model";
import {
    selectConsultations
} from "@app/guardian/enrollment/consultation/store/consultation.selector";
import {
    FindConsultationByChildAction,
    UpdateConsultationAction
} from "@app/therapist/enrollment/consultation/store/consultation.action";
import {selectConsultation} from "@app/therapist/enrollment/consultation/store/consultation.selector";
import {Evaluation} from "@app/shared/models/enrollment/evaluation.model";
import {FindEvaluationByChildAction} from "@app/guardian/enrollment/evaluation/store/evaluation.action";
import {selectEvaluation} from "@app/guardian/enrollment/evaluation/store/evaluation.selector";
import {Comment} from "@app/shared/models/enrollment/comment.model";
import {selectComments} from "@app/therapist/enrollment/comment/store/comment.selector";
import {
    FindPagedCommentsAction, RemoveCommentAction,
    SaveCommentAction,
    UpdateCommentAction
} from "@app/therapist/enrollment/comment/store/comment.action";
import {Course} from "@app/staff/learning/model/course.model";
import {
    RemoveSetupCourseSectionAction,
    SaveCourseAction,
    UpdateCourseAction
} from "@app/staff/learning/course/store/course.action";
import {CourseSection} from "@app/staff/learning/model/course-section.model";


@Component({
    selector: 'dex-child-profile',
    templateUrl: './child-profile.page.html',
    styleUrls: ['./child-profile.page.css'],
})


export class ChildProfilePage implements OnInit,AfterViewInit {

    childForm: FormGroup;
    consultationForm: FormGroup;
    child : Child;
    child$ : Observable<Child>;
    calibration$: Observable<Calibration>;
    evaluation$: Observable<Evaluation>;
    enrollment$: Observable<Enrollment>;
    consultation$ : Observable<Consultation>;
    consultation : Consultation;
    consultations$ : Observable<Consultation[]>;
    consultationResult$ : Observable<ConsultationResult>;
    comments$ : Observable<Comment[]>;
    totalSize$ :Observable<number>;
    data: any;
    dataLine: any;
    options : any;
    optionsLine : any;
    dataLabels: string[];
    scores: number[];
    fullScores : number[];
    expectedScores : number[];
    selectedCode: Therapist;
    selectedComment : Comment;
    displayDialog: boolean = false;
    colors: string[];
    @ViewChild('chart') chart: UIChart;
    private sectionResponses$: Observable<CalibrationSectionResponse>;

    cols = [
        {field: 'key', header: 'Key'},
        {field: 'value', header: 'Value'},
    ];

    breadcrumbs = [
        {label: 'Enrollment'},
        {label: 'Child Profile'},
    ];

    constructor(public http: HttpClient,
                public breadcrumbService: BreadcrumbService,
                public confirmationService: ConfirmationService,
                public fb: FormBuilder,
                private router: Router,
                public route: ActivatedRoute,
                public store: Store<GuardianEnrollmentState>,
                public cdr: ChangeDetectorRef) {

        this.route.params.subscribe((params: { code: string })=> {
            this.store.dispatch(
                new FindChildByCodeAction(params));
        });

        this.route.params.subscribe((params: { code: string })=> {
            this.store.dispatch(
                new FindConsultationByChildAction(params));
        });

        this.route.params.subscribe((params: { code: string }) => {
            this.breadcrumbs.push({label: params.code});
            this.store.dispatch(new FindEvaluationByChildAction(params));
        });

        this.route.params.subscribe((params: { code: string }) => {
            this.breadcrumbs.push({label: params.code});
            this.store.dispatch(new FindCalibrationByChildAction(params));
        });

        this.child$ = this.store.pipe(select(selectChild));
        this.consultations$ = this.store.pipe(select(selectConsultations));
        this.consultation$ = this.store.pipe(select(selectConsultation));
        this.evaluation$ = this.store.pipe(select(selectEvaluation));
        this.calibration$ = this.store.pipe(select(selectCalibration));
        this.enrollment$ = this.store.pipe(select(selectEnrollment));
        this.comments$ = this.store.pipe(select(selectComments));


        this.child$.subscribe(child=>{
            if(child.enrollment)
            {
                this.store.dispatch(new FindEnrollmentByChildAction({code: child.code}));
            }
        })

    }



    ngOnInit() {
        this.childForm = this.fb.group({
            age: ['', Validators.required],
            name: ['', Validators.required],
            gender: [null, Validators.required],
            concernType: [null, Validators.required],
            remark: [null, Validators.required],

        });

        this.child$.subscribe(data => {
            this.childForm.patchValue(data);
        });

        this.consultationForm = this.fb.group({
            comment: ['', Validators.required],
        });

        this.consultation$.subscribe(data => {
            this.consultationForm.patchValue(data);
        });

        this.route.params.subscribe((params: { code: string }) => {
            this.breadcrumbs.push({label: params.code});
            this.store.dispatch(new FindCalibrationByChildAction(params));
        });

        // working on section responses score
        this.sectionResponses$ = this.store.pipe(select(selectSectionResponses));
        this.sectionResponses$
            .pipe(
                filter((responses: CalibrationSectionResponse[],) => responses.length > 0),
                untilDestroyed(this),
            ).subscribe((responses: CalibrationSectionResponse[]) => {

            this.dataLabels = [];
            for (const response of responses) {
                this.dataLabels.push(response.evaluationSection.description);
            }

            this.scores = [];
            for (const response of responses) {
                this.scores.push(response.sectionScore);
            }
            // console.log(this.scores);

            this.fullScores = [];
            for (const response of responses) {
                this.fullScores.push(response.fullScore);
            }
            // console.log(this.fullScores);

            this.expectedScores = [];
            for (const response of responses) {
                this.expectedScores.push(response.fullScore/2);
            }

            this.colors = [];
            for (const response of responses) {
                this.colors.push('#' + Math.floor(Math.random() * 16777215).toString(16));
            }

            this.dataLine = {
                labels: ['Mendengar', 'Pemahaman Bahasa', 'Penghasilan Bahasa', 'Pragmatik', 'Kognisi', 'Sosial',],
                datasets : [
                    {
                        label: 'Full Score',
                        backgroundColor: 'rgba(173,255,47,0.2)',
                        borderColor: 'rgba(173,255,47,1)',
                        pointBackgroundColor: 'rgba(173,255,47,1)',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: 'rgba(173,255,47,1)',
                        data: this.fullScores,
                    },
                    {
                        label: 'Your Score',
                        backgroundColor: 'rgba(0,191,255,0.2)',
                        borderColor: 'rgba(0,191,255,1)',
                        pointBackgroundColor: 'rgba(0,191,255,1)',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: 'rgba(0,191,255,1)',
                        data: this.scores,
                    }

                ]
            };

            this.options = {
                scale: {
                    ticks : {
                        min : 0,
                        stepSize: 2
                    }
                }
            };
            this.optionsLine = {
                scales: {
                    yAxes: [{
                        stacked: false,
                        ticks: { min: 0 }
                    }]
                }
            };
        });



    }

    ngAfterViewInit(): void {
    }

    ngOnDestroy() {
    }

    isFormValid():boolean{
        return this.consultationForm.valid && !this.consultationForm.dirty;
    }

    updateConsultation(){
        this.child$.pipe(take(1)).subscribe( child => {
            this.consultation= this.consultationForm.value;
            this.store.dispatch(new UpdateConsultationAction({code :child.code , consultation : this.consultation}))
        })
    }

    evaluationResult(): void {
        this.child$.pipe(take(1)).subscribe( child =>{
            this.router.navigate(['/therapist/enrollment/evaluation-child-result/', child.evaluation.code]);
        })
    }

    loadData(event : any) {
        this.consultation$.subscribe(consultation=>{
            let limit = event.rows;
            let offset = event.first;
            let page = (offset / limit) + 1;
            this.store.dispatch(new FindPagedCommentsAction({code: consultation.code,filter:' ',page: page}))
        })

    }

    createComment(){
        this.showCommentEditorDialog();
    }

    onSaveComment(item: Comment) {
        this.consultation$.pipe(take(1)).subscribe(consultation=>{
            console.log(item);
            this.store.dispatch(new SaveCommentAction({code:consultation.code,comment: item}));

        })
        this.hideDialog();
    }

    onUpdateComment(item: Comment) {
        this.consultation$.pipe(take(1)).subscribe(consultation=>{
            console.log(item);
            this.store.dispatch(new UpdateCommentAction({code:consultation.code,comment: item}));

        })
        this.hideDialog();
    }

    deleteComment(comment: Comment) {
        this.consultation$.pipe(take(1)).subscribe(consultation=> {
            this.confirmationService.confirm({
                header: 'Confirmation',
                rejectVisible: true,
                message: 'Are you sure want to delete this comment?',
                acceptLabel: 'Yes',
                rejectLabel: 'No',
                accept: () => {
                    this.store.dispatch(new RemoveCommentAction({code:consultation.code,comment: comment}));
                    this.hideDialog();
                }
            });
        })

    }

    showCommentEditorDialog(){
        this.displayDialog = true;
    }

    updateComment(comment:Comment){
        this.selectedComment = comment;
        this.displayDialog = true;
    }

    showDialog() {
        this.displayDialog = true;
    }

    hideDialog() {
        this.displayDialog = false;
        this.selectedComment = null;
    }

}





