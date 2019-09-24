import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {BreadcrumbService} from '@app/component/breadcrumb.service';
import {ConfirmationService, Message} from 'primeng/api';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {select, Store} from '@ngrx/store';
import {
    selectTherapist,
    selectTherapistProfile,
} from '@app/therapist/profile/therapist-profile/store/therapist.selector';
import {
    FindTherapistAction, FindTherapistProfileAction,
    TherapistUpdateAction,
} from '@app/therapist/profile/therapist-profile/store/therapist.action';
import {Therapist} from '@app/shared/models/identity/therapist.model';
import {AppState} from '@app/core/core.state';
import {Attachment} from "@app/modules/common/components/attachment.model";
import {FileUpload} from "primeng/primeng";
import {selectAttachments} from "@app/modules/common/components/attachment.selector";
import {
    AddAttachmentAction,
    FindAttachmentsAction,
    RemoveAttachmentAction
} from "@app/modules/common/components/attachment.action";
import {AttachmentType} from "@app/modules/common/components/attachment-type.enum";
import {uniqueUsernameValidator} from "@app/component/username-validator-directive";
import {ConfirmPasswordValidator} from "@app/component/confirm-password.validator";
import {RegistrationService} from "@app/services/registration.service";
import {TherapistProfile} from "@app/therapist/profile/therapist-profile/component/therapist-profile.model";
import {User} from "@app/shared/models/identity/user.model";
import {take} from "rxjs/operators";


@Component({
    selector: 'dex-therapist-profile',
    templateUrl: './therapist-profile.page.html',
    styleUrls: ['./therapist-profile.page.css']
})


export class TherapistProfilePage implements OnInit {

    therapistProfile : TherapistProfile;
    therapistProfile$ : Observable<TherapistProfile>;
    therapist: Therapist;
    therapist$: Observable<Therapist>;
    therapistForm: FormGroup;
    userForm: FormGroup;
    supportingDocuments$: Observable<Attachment[]>;
    supportingDocuments: Attachment[];
    selectedSupportingDocuments: Attachment[];
    selectedUser : User;
    msgs: Message[];
    @ViewChild("fileUpload") fileUpload: FileUpload;
    documentClassName = 'com.irichment.identity.domain.model.DexTherapist';
    showAttachmentDialog: boolean = false;
    displayTherapistUserEditorDialog: boolean = false;


    cols = [
        {field: 'key', header: 'Key'},
        {field: 'value', header: 'Value'},
    ];


    breadcrumbs = [
        {label: 'Therapist'},
        {label: 'Profile'},
    ];

    constructor(public http: HttpClient,
                public breadcrumbService: BreadcrumbService,
                public confirmationService: ConfirmationService,
                public registrationService: RegistrationService,
                public fb: FormBuilder,
                public  router: Router,
                public route: ActivatedRoute,
                public store: Store<AppState>,
                public cdr: ChangeDetectorRef) {
        this.store.dispatch(new FindTherapistProfileAction());
        this.therapist$ = this.store.pipe(select(selectTherapist));
        this.therapistProfile$ = this.store.pipe(select(selectTherapistProfile));
        this.supportingDocuments$ = this.store.pipe(select(selectAttachments));
    }

    ngOnInit() {
        this.userForm = this.fb.group({
            name: ['', Validators.required,uniqueUsernameValidator(this.registrationService)],
            realName: ['', Validators.required],
            password: ['', Validators.required],
            confirmPassword: ['', Validators.required]
        },{
            validator: ConfirmPasswordValidator.MatchPassword,
        });

        this.therapistForm = this.fb.group({
            code: [''],
            name: ['', [Validators.required, Validators.minLength(3)]],
            identityNo: ['', Validators.required],
            hospital: ['', Validators.required],
            profession: ['', Validators.required],
            email: ['', Validators.required],
            mobile: ['', Validators.required],
            address1: ['', Validators.required],
            address2: ['', Validators.required],
            address3: ['', Validators.required],
            postcode: ['', Validators.required],
        });


        this.therapistProfile$.subscribe(data => {
            console.log(data);
            this.therapistForm.patchValue(data.therapist);
            this.userForm.patchValue(data.user);
            this.therapist = data.therapist;
            this.store.dispatch(new FindAttachmentsAction({
                documentId: this.therapist.id, documentClassName: this.documentClassName,
                attachmentType: AttachmentType.CERTIFICATE
            }));

            if (this.fileUpload) {
                this.fileUpload.clear();
            }
        });

        this.supportingDocuments$.subscribe(data => {
            this.supportingDocuments = data;
            this.clearSelectedAttachments();
        })
    }

    isFormValid(): boolean {
        return this.therapistForm.valid && this.therapistForm.dirty;
    }

    updateProfile() {
        this.therapist = this.therapistForm.value;
        this.store.dispatch(new TherapistUpdateAction(this.therapist));

    }


    inDevelopment() {
        this.confirmationService.confirm({
            header: 'Temporarily closed for development',
            message: 'Sorry, this site is under construction and will be back soon. Thank you.',
            acceptLabel: 'Ok',
            rejectVisible: false,
            accept: () => {
                console.log('accepted');
            }
        });
    }

    onUpload(event) {

        var attachment = {
            file: event.files[0],
            attachmentType: AttachmentType.CERTIFICATE
        };

        this.store.dispatch(new AddAttachmentAction({
            documentId: this.therapist.id,
            documentClassName: this.documentClassName,
            attachment: attachment
        }));
        this.fileUpload.clear();

    }

    onSuccessUpload() {
        this.fileUpload.clear();
    }

    doViewAttachment(attachment: Attachment) {
        this.selectedSupportingDocuments.push(attachment);
        this.showAttachmentDialog = true;
    }

    doViewAllAttachment() {
        this.selectedSupportingDocuments.concat(this.supportingDocuments);
    }

    hideAttachmentDialog() {
        this.showAttachmentDialog = false;
        this.clearSelectedAttachments();
    }

    doShowTherapistUserDialog() {
       this.therapistProfile$.pipe(take(1)).subscribe( data=>{
           this.selectedUser = data.user
       });
        this.displayTherapistUserEditorDialog = true;
    }

    hideTherapistUserEditorDialog() {
        this.displayTherapistUserEditorDialog = false;
    }

    doDeleteAttachment(attachment: Attachment) {
        this.store.dispatch(new RemoveAttachmentAction({
            documentId: this.therapist.id, documentClassName: this.documentClassName,
            attachment: attachment
        }));
    }

    clearSelectedAttachments() {
        this.selectedSupportingDocuments = [] as Attachment[];
    }
}


