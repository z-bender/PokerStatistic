import {NgModule} from '@angular/core';
import {
  AddItemComponent,
  AllPeriodResultsComponent,
  BankrollComponent,
  ItemsResultsTableComponent,
  MonthResultsComponent,
  ResultsTableComponent,
  YearResultsComponent
} from './index';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {BankrollApiService} from '../_services/BankrollApiService';
import {AbstractApiService} from '../_services/AbstractApiService';
import {CommonModule} from '@angular/common';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SettingsService} from '../_services/SettingsService';

@NgModule({
  declarations: [BankrollComponent, AddItemComponent, MonthResultsComponent, ItemsResultsTableComponent,
    AllPeriodResultsComponent, ResultsTableComponent, YearResultsComponent],
  providers: [BankrollApiService, AbstractApiService, SettingsService],
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
