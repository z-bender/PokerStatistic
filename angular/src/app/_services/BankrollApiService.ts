import {Injectable} from '@angular/core';
import {BankrollItem} from '../bankroll/bankroll-item';
import {AbstractApiService} from './AbstractApiService';

@Injectable()
export class BankrollApiService extends AbstractApiService {

  private bankrollApiUrl = this.apiUrl + '/bankroll';

  add(item: BankrollItem, successCallback) {
    const url: string = this.bankrollApiUrl + '/add';
    this.post(url, item).subscribe(
        result => successCallback(this.getBankrollItemFromApiResponse(result)),
        error => console.log('Error in add: ' + error)
      );
  }

  getLast(successCallback): void {
    const url: string = this.bankrollApiUrl + '/getLast';
    this.get(url).subscribe(
      result => successCallback(this.getBankrollItemFromApiResponse(result)),
      error => console.log('Error in getLast:' + error)
    );
  }

  getBankrollItemFromApiResponse(response): BankrollItem {
    const bankrollItem: BankrollItem = response.json();
    this.parseDateFromApiResponse(bankrollItem);
    return bankrollItem;
  }

  parseDateFromApiResponse(item: BankrollItem): void {
    const dateArray = item.dateTime;
    item.dateTime = new Date(dateArray[0], dateArray[1] - 1, dateArray[2]);
  }

}
