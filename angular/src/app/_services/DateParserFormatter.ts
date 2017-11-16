import {Injectable} from '@angular/core';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap/datepicker/datepicker.module';

@Injectable()
export class DateParserFormatter {

  getDateFromNgbDate(ngbDate: NgbDateStruct): Date {
    return new Date(ngbDate.year, ngbDate.month - 1, ngbDate.day);
  }

  getNgbDateFromDate(date: Date): NgbDateStruct {
    return {
      year: date.getFullYear(),
      month: date.getMonth() + 1,
      day: date.getDate()
    }
  }

  getDateFromApiLocalDate(localDate: number[]): Date {
    return new Date(localDate[0], localDate[1] - 1, localDate[2]);
  }

}
