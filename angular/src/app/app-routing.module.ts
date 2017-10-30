import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {routs} from './routs';

@NgModule({
  imports: [RouterModule.forRoot(routs)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
