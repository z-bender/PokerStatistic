import {BankrollComponent} from "./index";
import {AddItemComponent, MonthResultsComponent, AllPeriodResultsComponent} from "./bankroll/index";

export const routs = [
  {path: 'bankroll', component: BankrollComponent},
  {path: 'addItem', component: AddItemComponent},
  {path: 'monthResults', component: MonthResultsComponent},
  {path: 'allPeriodResults', component: AllPeriodResultsComponent},
  {path: '', redirectTo: 'bankroll', pathMatch: 'full'}
];
