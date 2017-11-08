import {Component} from '@angular/core';
import {BankrollApiService} from '../../../_services/BankrollApiService';
import {ParentChildPeriodResults} from '../ParentChildPeriodResults';

@Component({
  moduleId: module.id,
  selector: 'year-results',
  template: `
    <input id="year" class="form-control" [(ngModel)]="year" (change)="getResults()" />
    <app-results-table *ngIf="results" [resultsArray]="[results.parent]"></app-results-table>
    <app-results-table *ngIf="results" [resultsArray]="results.child"></app-results-table>
  `
})
// todo: объеденить с AllPeriodResults?
export class YearResultsComponent {
  results: ParentChildPeriodResults;
  year: number;

  constructor(private bankrollApiService: BankrollApiService) {
  }

  getResults() {
    this.bankrollApiService.getYearResults(this.year)
      .subscribe(result => {
        this.results = result;
        console.log(result);
      });
  }

}
