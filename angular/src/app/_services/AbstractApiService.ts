import {Headers, Http, RequestOptions} from '@angular/http';
import {Injectable} from '@angular/core';

@Injectable()
export class AbstractApiService {
  protected apiUrl = 'http://localhost:9191/api';

  constructor(private http: Http) {
  }

  get(url: string) {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.get(url, options);
  }

  post(url: string, bodyObject) {
    const body = JSON.stringify(bodyObject);
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.post(url, body, options);
  }

}
