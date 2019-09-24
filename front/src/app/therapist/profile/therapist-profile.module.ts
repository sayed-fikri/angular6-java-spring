import {NgModule} from "@angular/core";
import {SharedModule} from "@app/shared/shared.module";
import {StoreModule} from "@ngrx/store";
import {EffectsModule} from "@ngrx/effects";
import {TherapistProfilePage} from "@app/therapist/profile/therapist-profile/pages/therapist-profile.page";
import {TherapistIdentityService} from "@app/services/therapist-identity.service";
import {TherapistEffect} from "@app/therapist/profile/therapist-profile/store/therapist.effect";
import {SystemService} from "@app/services";
import {FEATURE_NAME, reducers} from "@app/therapist/profile/therapist-profile/store/therapist-profile.state";
import {TherapistSupportingDocumentsPage} from '@app/therapist/profile/therapist-profile/pages/therapist-supporting-documents.page';
import {CommonModule} from '@app/modules/common/common.module';
import {TherapistUserEditorDialog} from "@app/therapist/profile/therapist-profile/component/therapist-user-editor.dialog";


@NgModule({
    imports: [
        SharedModule,
        CommonModule,
        StoreModule.forFeature(FEATURE_NAME, reducers),
        EffectsModule.forFeature([
            TherapistEffect,
        ]),
    ],
    declarations: [
        TherapistProfilePage,
        TherapistSupportingDocumentsPage,
        TherapistUserEditorDialog,

    ],
    exports: [],
    providers: [SystemService, TherapistIdentityService]

})

export class TherapistProfileModule {
    constructor() {
    }
}
