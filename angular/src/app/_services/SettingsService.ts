import {Injectable} from '@angular/core';
import {BankrollApiService} from './BankrollApiService';

@Injectable()
export class SettingsService {

  private _yearsIsGetting: boolean = false;
  private _firstItemYear: number;
  private _lastItemYear: number;

  constructor(private bankrollApiService: BankrollApiService) {
  }

  fillYearsOfStatistic(callbackYearArray: number[]) {
    if (this._yearsIsGetting) {
      this.fillYearsByFirstAndLastYears(callbackYearArray);
    } else {
      this.bankrollApiService.getStatisticPeriod().subscribe(
        period => {
          console.log('Period of statistic');
          console.log(period);
          this._firstItemYear = period.start.getFullYear();
          this._lastItemYear = period.end.getFullYear();
          this._yearsIsGetting = true;
          this.fillYearsByFirstAndLastYears(callbackYearArray);
        }
      )
    }
  }

  private fillYearsByFirstAndLastYears(callbackYearArray: number[]) {
    callbackYearArray.length = 0;
    for (let year: number = this._firstItemYear; year <= this._lastItemYear; year++) {
      callbackYearArray.push(year);
    }
  }

}
