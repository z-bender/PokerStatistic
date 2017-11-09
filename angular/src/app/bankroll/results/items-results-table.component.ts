import {Component, Input} from '@angular/core';
import {ItemResult} from './ItemResult';

@Component({
  moduleId: module.id,
  selector: 'app-items-results-table',
  templateUrl: 'items-results-table.component.html'
})
export class ItemsResultsTableComponent {

  @Input()
  itemsResults: ItemResult[];
}
