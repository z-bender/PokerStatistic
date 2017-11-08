import {BankrollComponent} from './index';
import {
  AddItemComponent,
  AllPeriodResultsComponent,
  MonthResultsComponent,
  YearResultsComponent
} from './bankroll/index';

export const routs = [
  {path: 'bankroll', component: BankrollComponent},
  {path: 'addItem', component: AddItemComponent},
  {path: 'monthResults', component: MonthResultsComponent},
  {path: 'allPeriodResults', component: AllPeriodResultsComponent},
  {path: 'yearResults', component: YearResultsComponent},
  {path: '', redirectTo: 'bankroll', pathMatch: 'full'}
];
