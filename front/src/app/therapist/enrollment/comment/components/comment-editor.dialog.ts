import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Comment} from "@app/shared/models/enrollment/comment.model";

@Component({
    selector: 'dex-comment-editor-dialog',
    templateUrl: './comment-editor.dialog.html'
})

export class CommentEditorDialog implements OnInit {

    isEdit: boolean = false;
    editorForm: FormGroup;
    @Input() comment: Comment;
    @Input() showDialog: boolean;
    @Output() onSave: EventEmitter<Comment> = new EventEmitter<Comment>();
    @Output() onSaveAndNew: EventEmitter<Comment> = new EventEmitter<Comment>();
    @Output() onUpdate: EventEmitter<Comment> = new EventEmitter<Comment>();
    @Output() onUpdateAndNew: EventEmitter<Comment> = new EventEmitter<Comment>();
    @Output() onDelete: EventEmitter<Comment> = new EventEmitter<Comment>();
    @Output() onClose: EventEmitter<boolean> = new EventEmitter<boolean>();

    constructor(public fb: FormBuilder) {
    }

    ngOnInit() {
        this.editorForm = this.fb.group({
            code: null,
            comment: [null, Validators.required],
        });
    }

    createItem(): Comment{
        return {
            id: 0,
            comment: null,
            consultation: null,
            code: null,
        };
    }

    saveItem() {
        this.onSave.emit(this.editorForm.value);
        this.editorForm.reset();
        this.focusOnInput();
    }

    updateItem() {
        this.onUpdate.emit(this.editorForm.value);
        this.editorForm.reset();
        this.focusOnInput();
    }

    deleteItem() {
        this.onDelete.emit(this.editorForm.value);
        this.editorForm.reset();
    }

    onShow() {
        this.isEdit = this.comment != null;
        if (this.isEdit) {
            this.editorForm.patchValue(this.comment);
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
            // this.invoiceNoField.nativeElement.focus();
        }, 0);
    }

    ngDestroy() {
        this.onSave.unsubscribe();
        this.onSaveAndNew.unsubscribe();
        this.onDelete.unsubscribe();
        this.onClose.unsubscribe();
    }
}
