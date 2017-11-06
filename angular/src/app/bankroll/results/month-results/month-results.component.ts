import {Component} from "@angular/core";
import {PeriodResults} from "../period-results";
import {BankrollApiService} from "../../../_services/BankrollApiService";

@Component({
  moduleId: module.id,
  selector: 'app-month-results',
  templateUrl: 'month-results.component.html'
})
export class MonthResultsComponent {

  monthResults: PeriodResults;
  year: number;
  month: number;

  constructor(private bankrollApiService: BankrollApiService) {
  }

  updatePeriod(): void {
    console.log(this.year);
    if(this.year && this.month) {
      this.bankrollApiService.getMonthResults(this.year, this.month)
        .subscribe(result => {
          this.monthResults = result;
          console.log(result);
        });
    }
  }


}