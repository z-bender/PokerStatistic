import {NgModule} from '@angular/core';
import {AddItemComponent, BankrollComponent, BankrollItem, MonthResultsComponent} from './index';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {BankrollApiService} from '../_services/BankrollApiService';
import {AbstractApiService} from '../_services/AbstractApiService';
import {CommonModule, DatePipe} from '@angular/common';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateParserFormatter} from '../_services/NgbDateParserFormatter';

@NgModule({
  declarations: [BankrollComponent, AddItemComponent, MonthResultsComponent],
  providers: [BankrollItem, AddItemComponent, BankrollApiService, AbstractApiService, DatePipe, NgbDateParserFormatter],
  imports: [FormsModule, HttpModule, ReactiveFormsModule, CommonModule, NgbModule]
})
export class BankrollModule {

  constructor() {
    // for exclude change time for timezone
    Date.prototype.toJSON = function () {
      const utcIgnoreTimeZone = new Date(Date.UTC(this.getFullYear(), this.getMonth(), this.getDate(), this.getHours(), this.getMinutes()));
      return utcIgnoreTimeZone.toISOString();
    };
  }

}
