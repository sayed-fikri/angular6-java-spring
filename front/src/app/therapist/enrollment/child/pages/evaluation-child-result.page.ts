import {FormBuilder} from "@angular/forms";
import {AfterViewInit, Component, OnInit, ViewChild, ViewEncapsulation} from "@angular/core";
import {select, Store} from "@ngrx/store";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";
import {BreadcrumbService} from "@app/component/breadcrumb.service";
import {ConfirmationService, MenuItem} from "primeng/api";
import {filter, take} from "rxjs/operators";
import {UIChart} from 'primeng/chart';
import {untilDestroyed} from 'ngx-take-until-destroy';
import {Evaluation} from "@app/shared/models/enrollment/evaluation.model";
import {EvaluationSectionResponse} from "@app/shared/models/enrollment/evaluation-section-response.model";
import {GuardianEnrollmentState} from "@app/guardian/enrollment/guardian-enrollment.state";
import {FindEvaluationByCodeAction} from "@app/guardian/enrollment/evaluation/store/evaluation.action";
import {selectEvaluation, selectSectionResponses} from "@app/guardian/enrollment/evaluation/store/evaluation.selector";


@Component({
    selector: 'dex-evaluation-child-result-page',
    templateUrl: './evaluation-child-result.page.html',
    encapsulation: ViewEncapsulation.None
})
export class EvaluationChildResultPage implements OnInit, AfterViewInit {

    evaluation: Evaluation;
    items: MenuItem[];
    activeIndex: number = 1;
    data: any;
    dataLine: any;
    options: any;
    optionsLine: any;
    dataLabels: string[];
    scores: number[];
    fullScores: number[];
    expectedScores: number[];
    colors: string[];
    @ViewChild('chart') chart: UIChart;

    breadcrumbs = [
        {label: 'Enrollment'},
        {label: 'Register'},
        {label: 'Evaluation'},
        {label: 'Result'},
    ];

    evaluation$: Observable<Evaluation>;

    openedIndex: number = 0;
    private sectionResponses$: Observable<EvaluationSectionResponse>;

    constructor(public breadcrumbService: BreadcrumbService,
                public fb: FormBuilder,
                public route: ActivatedRoute,
                public router: Router,
                public confirmationService: ConfirmationService,
                public store: Store<GuardianEnrollmentState>) {
        this.route.params.subscribe((params: { code: string }) => {
            this.store.dispatch(new FindEvaluationByCodeAction(params));
        });

        this.evaluation$ = this.store.pipe(select(selectEvaluation));
        this.breadcrumbService.setItems(this.breadcrumbs);
        this.evaluation$
            .subscribe(evaluation => this.evaluation = evaluation);
    }

    openIndex(index) {
        console.log(index);
        this.openedIndex = index;
    }

    ngOnInit() {

        this.items = [
            {
                label: 'Register',
                command: (event: any) => {
                    this.activeIndex = 0;
                }
            },
            {
                label: 'Evaluation',
                command: (event: any) => {
                    this.activeIndex = 1;
                }
            },
            {label: 'Enrollment'},
            {label: 'Payment'},
        ];

        this.route.params.subscribe((params: { code: string }) => {
            this.breadcrumbs.push({label: params.code});
            this.store.dispatch(new FindEvaluationByCodeAction(params));
        });

        // working on section responses score
        this.sectionResponses$ = this.store.pipe(select(selectSectionResponses));
        this.sectionResponses$
            .pipe(
                filter((responses: EvaluationSectionResponse[],) => responses.length > 0),
                untilDestroyed(this),
            ).subscribe((responses: EvaluationSectionResponse[]) => {

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
                this.expectedScores.push(response.fullScore / 2);
            }

            this.colors = [];
            for (const response of responses) {
                this.colors.push('#' + Math.floor(Math.random() * 16777215).toString(16));
            }

            this.data = {
                labels: this.dataLabels,
                datasets: [
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
                        label: 'Expected Score',
                        backgroundColor: 'rgba(250,128,114,0.2)',
                        borderColor: 'rgba(250,128,114,1)',
                        pointBackgroundColor: 'rgba(250,128,114,1)',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: 'rgba(250,128,114,1)',
                        data: this.expectedScores
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

            this.dataLine = {
                labels: ['Mendengar','Pemahaman Bahasa','Penghasilan Bahasa','Pragmatik','Kognisi','Sosial',],
                datasets: [
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
                    ticks: {
                        min: 0,
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

    redirectProfile(){
        this.evaluation$.pipe(take(1)).subscribe(evaluation =>{
            this.router.navigate(['/therapist/enrollment/children-profile', evaluation.child.code]);
        })
    }

}
