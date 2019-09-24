import {SharedModule} from "@app/shared/shared.module";
import {NgModule} from "@angular/core";
import {StoreModule} from "@ngrx/store";
import {EffectsModule} from "@ngrx/effects";
import {ChildEffects} from "./child/store/child.effect";
import {FEATURE_NAME, reducers} from "./therapist-enrollment.state";
import {SystemService} from "@app/services";
import {TherapistEnrollmentService} from "@app/services/therapist-enrollment.service";
import {ConsultationEffects} from "@app/therapist/enrollment/consultation/store/consultation.effect";
import {ConsultationListPage} from "@app/therapist/enrollment/consultation/pages/consultation-list.page";
import {ChildProfilePage} from "@app/therapist/enrollment/child/pages/child-profile.page";
import {EvaluationChildResultPage} from "@app/therapist/enrollment/child/pages/evaluation-child-result.page";
import {CommentListPage} from "@app/therapist/enrollment/comment/pages/comment-list.page";
import {CommentEditorDialog} from "@app/therapist/enrollment/comment/components/comment-editor.dialog";
import {CommentEffects} from "@app/therapist/enrollment/comment/store/comment.effect";
import {EvaluationLogEffects} from "@app/therapist/enrollment/evaluation-log/store/evaluation-log.effect";


@NgModule({
    imports: [
        SharedModule,
        StoreModule.forFeature(FEATURE_NAME, reducers),
        EffectsModule.forFeature([
            ChildEffects,
            ConsultationEffects,
            CommentEffects,
            EvaluationLogEffects
        ]),
    ],
    declarations: [
        ChildProfilePage,
        ConsultationListPage,
        EvaluationChildResultPage,
        CommentListPage,
        CommentEditorDialog
    ],
    exports: [],
    providers: [SystemService, TherapistEnrollmentService,],

})

export class TherapistEnrollmentModule {
    constructor() {
    }
}
