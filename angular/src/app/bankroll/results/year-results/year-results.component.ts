import {Component, OnInit} from '@angular/core';
import {BankrollApiService} from '../../../_services/BankrollApiService';
import {ParentChildPeriodResults} from '../ParentChildPeriodResults';
import {SettingsService} from '../../../_services/SettingsService';

@Component({
  moduleId: module.id,
  selector: 'year-results',
  template: `
    <select #yearSelect class="form-control" (change)="getResults(yearSelect.value)">
      <option disabled hidden>-- select year --</option>
      <option *ngFor="let year of years" [value]="year" [label]="year"></option>
    </select>
    <app-results-table *ngIf="results" [resultsArray]="[results.parent]"></app-results-table>
    <app-results-table *ngIf="results" [resultsArray]="results.child"></app-results-table>
  `
})
// todo: объеденить с AllPeriodResults?
export class YearResultsComponent implements OnInit {
  results: ParentChildPeriodResults;
  years: number[] = [];

  constructor(private bankrollApiService: BankrollApiService, private settingsService: SettingsService) {
  }

  ngOnInit() {
    this.settingsService.fillYearsOfStatistic(this.years);
  }

  getResults(year: number) {
    this.bankrollApiService.getYearResults(year)
      .subscribe(result => {
        this.results = result;
        console.log(result);
      });
  }

}
