import {Component, OnInit} from "@angular/core";
import {PeriodResults} from "../period-results";
import {BankrollApiService} from "../../../_services/BankrollApiService";

@Component({
  moduleId: module.id,
  selector: 'all-period-results',
  template: '<app-results-item *ngIf="results" [resultsItem]="results"></app-results-item>'
})
export class AllPeriodResultsComponent implements OnInit {
  results: PeriodResults;

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
