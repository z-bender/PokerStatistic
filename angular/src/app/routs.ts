import {BankrollComponent} from './index';
import {AddItemComponent} from './bankroll/index';

export const routs = [
  {path: 'bankroll', component: BankrollComponent},
  {path: 'addItem', component: AddItemComponent},
  {path: '', redirectTo: 'bankroll', pathMatch: 'full'}
];
