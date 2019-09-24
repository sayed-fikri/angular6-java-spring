import {Routes} from "@angular/router";
import {TherapistAppShellComponent} from "../app-shell/therapist-app-shell.component";
import {AuthGuard} from "../core/auth/auth.guard";
import {TherapistDashboard} from "./therapist-dashboard.page";
import {NotFoundPage} from "../static/pages/not-found.page";
import {ChildProfilePage} from "@app/therapist/enrollment/child/pages/child-profile.page";
import {ConsultationListPage} from "@app/therapist/enrollment/consultation/pages/consultation-list.page";
import {TherapistProfilePage} from "@app/therapist/profile/therapist-profile/pages/therapist-profile.page";
// import {TherapistProfileEditPage} from "@app/therapist/profile/therapist-profile/pages/therapist-profile-edit.page";
import {EvaluationChildResultPage} from "@app/therapist/enrollment/child/pages/evaluation-child-result.page";
import {CommentListPage} from "@app/therapist/enrollment/comment/pages/comment-list.page";
import {TherapistSupportingDocumentsPage} from '@app/therapist/profile/therapist-profile/pages/therapist-supporting-documents.page';

export const therapistRoutes: Routes = [
    {
        path: 'therapist',
        component: TherapistAppShellComponent,

        canActivate: [AuthGuard],
        children: [
            {path: '', component: TherapistDashboard},
            {
                path: 'therapist-profile',
                component: TherapistProfilePage,
                children: []
            },
            {
                path: 'therapist-supporting-documents',
                component: TherapistSupportingDocumentsPage,
                children: []
            },
            {
                path: 'enrollment',
                children: [
                    {
                        path: 'children-profile/:code',
                        component: ChildProfilePage,
                    },
                    {
                        path: 'children-list',
                        component: ConsultationListPage,
                    },
                    {
                        path: 'evaluation-child-result/:code',
                        component: EvaluationChildResultPage,
                    },
                    {
                        path: 'children-list/:code/comments/',
                        component: CommentListPage,
                    },
                ]
            },
            {path: '**', component: NotFoundPage},
        ]
    },
];

