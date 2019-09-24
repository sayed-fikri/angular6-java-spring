import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Course} from "@app/staff/learning/model/course.model";
import {User} from "@app/shared/models/identity/user.model";
import {uniqueUsernameValidator} from "@app/component/username-validator-directive";
import {ConfirmPasswordValidator} from "@app/component/confirm-password.validator";
import {RegistrationService} from "@app/services/registration.service";
import {RemoveAttachmentAction} from "@app/modules/common/components/attachment.action";
import {Store} from "@ngrx/store";
import {AppState} from "@app/core/core.state";
import {TherapistUserUpdateAction} from "@app/therapist/profile/therapist-profile/store/therapist.action";

@Component({
    selector: 'dex-therapist-user-editor-dialog',
    templateUrl: './therapist-user-editor.dialog.html',
    styleUrls: ['./therapist-user-editor.dialog.css']
})

export class TherapistUserEditorDialog implements OnInit {

    isEdit: boolean = false;
    editorForm: FormGroup;
    @Input() user: User;
    @Input() showDialog: boolean;
    @Output() onUpdate: EventEmitter<User> = new EventEmitter<User>();
    @Output() onClose: EventEmitter<boolean> = new EventEmitter<boolean>();

    constructor(public fb: FormBuilder,
                private registrationService : RegistrationService,
                public store: Store<AppState>,) {
    }

    ngOnInit() {
        this.editorForm = this.fb.group({
            name: ['', Validators.required,uniqueUsernameValidator(this.registrationService)],
            password: ['', Validators.required],
            confirmPassword: ['', Validators.required]
        },{
            validator: ConfirmPasswordValidator.MatchPassword,
        });
    }

    createItem(): User {
        return {
            id: null,
            name: null,
            realName: null,
            email: null,
            password: null,
            principalType: null,
        };
    }

    update() {
        // this.onUpdate.emit(this.editorForm.value);
        this.user.password = this.editorForm.value.password;
        this.store.dispatch(new TherapistUserUpdateAction(this.user));
        // console.log(this.user);
        this.editorForm.reset();
        this.focusOnInput();
        this.onHide();
    }

    onShow() {
        this.isEdit = this.user != null;
        if (this.isEdit) {
            this.editorForm.patchValue(this.user);
        } else {
            this.editorForm.patchValue(this.createItem());
        }
        this.focusOnInput();
    }

    onHide() {
        this.onClose.emit(false);
    }

    focusOnInput() {
        setTimeout(() => {
        }, 0);
    }

    ngDestroy() {
        this.onUpdate.unsubscribe();
        this.onClose.unsubscribe();
    }
}
