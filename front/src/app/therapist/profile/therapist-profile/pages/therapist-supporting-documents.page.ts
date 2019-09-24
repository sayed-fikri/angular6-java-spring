import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {BreadcrumbService} from '@app/component/breadcrumb.service';
import {ConfirmationService, Message} from 'primeng/api';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {select, Store} from '@ngrx/store';
import {selectTherapist,} from '@app/therapist/profile/therapist-profile/store/therapist.selector';
import {FindTherapistAction,} from '@app/therapist/profile/therapist-profile/store/therapist.action';
import {Therapist} from '@app/shared/models/identity/therapist.model';
import {AppState} from '@app/core/core.state';
import {
    AddAttachmentAction,
    FindAttachmentsAction,
    RemoveAttachmentAction
} from '@app/modules/common/components/attachment.action';
import {AttachmentType} from '@app/modules/common/components/attachment-type.enum';
import {Attachment} from '@app/modules/common/components/attachment.model';
import {selectAttachments} from '@app/modules/common/components/attachment.selector';
import {FileUpload} from 'primeng/fileupload';


@Component({
    selector: 'dex-therapist-supporting-documents',
    templateUrl: './therapist-supporting-documents.page.html',
    styleUrls: ['./therapist-supporting-documents.page.css']
})


export class TherapistSupportingDocumentsPage implements OnInit {

    therapist: Therapist;
    therapist$: Observable<Therapist>;
    supportingDocuments$: Observable<Attachment[]>;
    supportingDocuments: Attachment[];
    selectedSupportingDocuments: Attachment[];
    therapistForm: FormGroup;
    msgs: Message[];
    @ViewChild("fileUpload") fileUpload: FileUpload;
    documentClassName = 'com.irichment.identity.domain.model.DexTherapist';
    showAttachmentDialog: boolean = false;


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
                public fb: FormBuilder,
                public  router: Router,
                public route: ActivatedRoute,
                public store: Store<AppState>,
                public cdr: ChangeDetectorRef) {

        this.store.dispatch(new FindTherapistAction());
        this.therapist$ = this.store.pipe(select(selectTherapist));
        this.supportingDocuments$ = this.store.pipe(select(selectAttachments));
    }

    ngOnInit() {

        this.therapistForm = this.fb.group({
            name: ['', Validators.required],
        });

        this.therapist$.subscribe(data => {
            this.therapistForm.patchValue(data);
            this.therapist = data;
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


