import {Injectable, ReflectiveInjector} from '@angular/core';
import {BankrollItem} from '../bankroll/bankroll-item';
import {AbstractApiService} from './AbstractApiService';
import {Response, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {PeriodResults} from '../bankroll/results/PeriodResults';
import {ParentChildPeriodResults} from '../bankroll/results/ParentChildPeriodResults';
import {DatePeriod} from '../bankroll/results/DatePeriod';
import {DateParserFormatter} from './DateParserFormatter';

@Injectable()
export class BankrollApiService extends AbstractApiService {

  private bankrollApiUrl = this.apiUrl + '/bankroll';
  private dateParser: DateParserFormatter;

  add(item: BankrollItem): Observable<BankrollItem> {
    const url: string = this.bankrollApiUrl + '/add';
    console.log('Add item');
    console.log(item);
    return this.post(url, item)
      .map(response => this.getBankrollItemFromApiResponse(response))
      .catch(this.handleError);
  }

  getLast(): Observable<BankrollItem> {
    const url: string = this.bankrollApiUrl + '/getLast';
    return this.getWithoutParams(url)
      .map(response => this.getBankrollItemFromApiResponse(response))
      .catch(this.handleError);
  }

  private getBankrollItemFromApiResponse(response: Response): BankrollItem {
    const bankrollItem: BankrollItem = response.json();
    this.parseDateFromApiResponse(bankrollItem);
    return bankrollItem;
  }

  private parseDateFromApiResponse(item: BankrollItem) {
    const dateArray = item.dateTime;
    item.dateTime = new Date(dateArray[0], dateArray[1] - 1, dateArray[2]);
  }

  getMonthResults(year: number, month: number): Observable<PeriodResults> {
    const url: string = this.bankrollApiUrl + '/getMonthResults';
    // todo: DRY
    const params = new URLSearchParams();
    params.set("year", year.toString());
    params.set("month", month.toString());
    return this.get(url, params).map(this.getPeriodResultsFromApiResponse);
  }

  private getPeriodResultsFromApiResponse(response: Response): PeriodResults {
    const result: PeriodResults = response.json();
    return result;
  }

  private getParentChildResultsFromApiResponse(response: Response): ParentChildPeriodResults {
    const result: ParentChildPeriodResults = response.json();
    return result;
  }

  getAllPeriodResults(): Observable<ParentChildPeriodResults> {
    const url: string = this.bankrollApiUrl + '/getAllPeriodResults';
    return this.getWithoutParams(url).map(this.getParentChildResultsFromApiResponse);
  }

  getYearResults(year: number): Observable<ParentChildPeriodResults> {
    const url: string = this.bankrollApiUrl + '/getYearResults';
    // todo: DRY
    const params = new URLSearchParams();
    params.set("year", year.toString());
    return this.get(url, params).map(this.getParentChildResultsFromApiResponse);
  }

  getStatisticPeriod(): Observable<DatePeriod> {
    const url: string = this.bankrollApiUrl + '/getStatisticsPeriod';
    return this.getWithoutParams(url).map(this.getDatePeriodFromApiResponse);
  }

  private getDatePeriodFromApiResponse(response: Response): DatePeriod {
    let startLocalDate: number[] = response.json().start;
    let endLocalDate: number[] = response.json().end;
    let result: DatePeriod = new DatePeriod();
    // fixme: DateParserFormatter didn't autowiring in initialization of BankrollApiService
    if(this.dateParser == null) {
      const injector = ReflectiveInjector.resolveAndCreate([DateParserFormatter]);
      this.dateParser = injector.get(DateParserFormatter);
    }
    result.start = this.dateParser.getDateFromApiLocalDate(startLocalDate);
    result.end = this.dateParser.getDateFromApiLocalDate(endLocalDate);
    return result;
  }

}
