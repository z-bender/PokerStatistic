import {Injectable} from '@angular/core';

@Injectable()
export class NgbDateParserFormatter {

  toDate(ngbDate: NgbDateStruct): Date {
    return new Date(ngbDate.year, ngbDate.month - 1, ngbDate.day);
  }

  fromDate(date: Date): NgbDateStruct {
    return {
      year: date.getFullYear(),
      month: date.getMonth() + 1,
      day: date.getDate()
    }
  }

}

// for clarity
class NgbDateStruct {
  year;
  month;
  day;
}
