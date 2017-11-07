import {Component, OnInit} from '@angular/core';
import {BankrollApiService} from '../../../_services/BankrollApiService';
import {ParentChildPeriodResults} from '../ParentChildPeriodResults';

@Component({
  moduleId: module.id,
  selector: 'all-period-results',
  template: `
    <app-results-table *ngIf="results" [resultsArray]="[results.parent]"></app-results-table>
    <app-results-table *ngIf="results" [resultsArray]="results.child"></app-results-table>
  `
})
export class AllPeriodResultsComponent implements OnInit {
  results: ParentChildPeriodResults;

  constructor(private bankrollApiService: BankrollApiService) {
  }

  ngOnInit() {
    this.bankrollApiService.getAllPeriodResults()
      .subscribe(result => {
        this.results = result;
        console.log(result);
      });
  }

}
