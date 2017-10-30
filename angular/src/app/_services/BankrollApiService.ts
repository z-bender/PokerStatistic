import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptions} from '@angular/http';
import {BankrollItem} from '../bankroll/bankroll-item';
import {AbstractApiService} from './AbstractApiService';

@Injectable()
export class BankrollApiService extends AbstractApiService {

  private bankrollApiUrl = this.apiUrl + '/bankroll';

  constructor(private http: Http) {
    super();
  }

  add(item: BankrollItem) {
    const url: string = this.bankrollApiUrl + '/add';
    const body = JSON.stringify(item);
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    this.http.post(url, body, options)
      .subscribe(
        // fixme: ошибка в result
        result => console.log(result.json()),
        error => console.log(error.statusText)
      );
  }

  getLast(successCallback): void {
    const url: string = this.bankrollApiUrl + '/getLast';
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    this.http.get(url, options).subscribe(
      result => {
        const data: BankrollItem = result.json();
        data.dateTime = this.parseDateFromApiResponse(data.dateTime);
        successCallback(data);
      },
      error => console.log('Error in getLast:' + error.statusText)
    );
  }

  parseDateFromApiResponse(dateArray): Date {
    return new Date(dateArray[0], dateArray[1] - 1, dateArray[2]);
  }

}
