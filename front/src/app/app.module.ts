import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpClientModule} from '@angular/common/http';
import {ProgramService} from './service/program.service';
import {TableModule} from 'primeng/table';
import {ButtonModule, CardModule, InputTextModule} from 'primeng/primeng';
import {DynamicDialogModule} from 'primeng/dynamicdialog';
import {VirtualScrollerModule} from 'primeng/virtualscroller';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    HttpClientModule,
    TableModule,
    InputTextModule,
    ButtonModule,
    DynamicDialogModule,
    CardModule,
    VirtualScrollerModule,
  ],
  providers: [ProgramService],
  bootstrap: [AppComponent]
})
export class AppModule { }
