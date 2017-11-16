import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {AppRoutingModule} from './app-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AppComponent, BankrollModule} from './index';
import {DateParserFormatter} from './_services/DateParserFormatter';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    BankrollModule,
    AppRoutingModule,
    NgbModule.forRoot()
  ],
  providers: [DateParserFormatter],
  bootstrap: [AppComponent],
})
export class AppModule {
}
