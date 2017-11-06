import {Component, Input} from "@angular/core";
import {PeriodResults} from "./period-results";

@Component({
  moduleId: module.id,
  selector: 'app-results-item',
  templateUrl: 'results-item.component.html'
})
export class ResultsItemComponent {

  @Input()
  resultsItem: PeriodResults;
}
