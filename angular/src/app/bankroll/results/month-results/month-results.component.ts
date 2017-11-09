import {Component} from '@angular/core';
import {PeriodResults} from '../period-results';
import {BankrollApiService} from '../../../_services/BankrollApiService';

@Component({
  moduleId: module.id,
  selector: 'app-month-results',
  template: `
    <!-- todo: change to datepicker -->
    <input id="year" class="form-control" [(ngModel)]="year" (change)="updatePeriod()"/>
    <input id="month" class="form-control" [(ngModel)]="month" (change)="updatePeriod()"/>
    <app-results-table *ngIf="monthResults" [resultsArray]="[monthResults]"></app-results-table>
    <app-items-results-table *ngIf="monthResults" [itemsResults]="monthResults.itemsResults"></app-items-results-table>
  `
})
export class MonthResultsComponent {

  monthResults: PeriodResults;
  year: number;
  month: number;

  constructor(private bankrollApiService: BankrollApiService) {
  }

  updatePeriod(): void {
    console.log(this.year);
    if (this.year && this.month) {
      this.bankrollApiService.getMonthResults(this.year, this.month)
        .subscribe(result => {
          this.monthResults = result;
          console.log(result);
        });
    }
  }


}
