import {NgModule} from "@angular/core";
import {SystemService} from "../services";
import {TherapistDashboard} from "./therapist-dashboard.page";
import {TherapistEnrollmentModule} from "./enrollment/therapist-enrollment.module";
import {TherapistEnrollmentService} from "../services/therapist-enrollment.service";
import {CardModule} from "primeng/card";
import {SharedModule} from "@app/shared/shared.module";
import {TableModule} from "primeng/table";
import {TherapistProfileModule} from "@app/therapist/profile/therapist-profile.module";

@NgModule({
    imports: [
        TherapistProfileModule,
        TherapistEnrollmentModule,
        CardModule,
        TableModule,
        SharedModule
    ],
    declarations: [
        TherapistDashboard,

    ],
    exports: [],
    providers: [
        SystemService,
        TherapistEnrollmentService,
    ],

})
export class TherapistModule {
    constructor() {
    }
}
