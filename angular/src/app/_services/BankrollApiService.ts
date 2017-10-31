import {Injectable} from '@angular/core';
import {BankrollItem} from '../bankroll/bankroll-item';
import {AbstractApiService} from './AbstractApiService';
import {Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class BankrollApiService extends AbstractApiService {

  private bankrollApiUrl = this.apiUrl + '/bankroll';

  add(item: BankrollItem): Observable<BankrollItem> {
    const url: string = this.bankrollApiUrl + '/add';
    return this.post(url, item)
      .map(response => this.getBankrollItemFromApiResponse(response))
      .catch(this.handleError);
  }

  getLast(): Observable<BankrollItem> {
    const url: string = this.bankrollApiUrl + '/getLast';
    return this.get(url)
      .map(response => this.getBankrollItemFromApiResponse(response))
      .catch(this.handleError);
  }

  getBankrollItemFromApiResponse(response: Response): BankrollItem {
    const bankrollItem: BankrollItem = response.json();
    this.parseDateFromApiResponse(bankrollItem);
    return bankrollItem;
  }

  parseDateFromApiResponse(item: BankrollItem) {
    const dateArray = item.dateTime;
    item.dateTime = new Date(dateArray[0], dateArray[1] - 1, dateArray[2]);
  }

}
