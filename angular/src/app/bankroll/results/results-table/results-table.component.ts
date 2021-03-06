import {Component, Input} from '@angular/core';
import {PeriodResults} from '../PeriodResults';

@Component({
  moduleId: module.id,
  selector: 'app-results-table',
  templateUrl: 'results-table.component.html'
})
export class ResultsTableComponent {

  @Input()
  resultsArray: PeriodResults[];
}
