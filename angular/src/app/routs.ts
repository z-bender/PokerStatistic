import {BankrollComponent} from './index';
import {AddItemComponent, MonthResultsComponent} from './bankroll/index';

export const routs = [
  {path: 'bankroll', component: BankrollComponent},
  {path: 'addItem', component: AddItemComponent},
  {path: 'monthResults', component: MonthResultsComponent},
  {path: '', redirectTo: 'bankroll', pathMatch: 'full'}
];
