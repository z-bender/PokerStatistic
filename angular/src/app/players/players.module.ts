import {NgModule} from '@angular/core';
import {PlayersComponent} from './players.component';
import {PlayerComponent} from './player.component';
import {CommonModule} from '@angular/common';
import {PlayersService} from './PlayersService';
import {PlayersRoutingModule} from './players-routing.module';
import {PlayerResolve} from './player.resolve';

@NgModule({
  declarations: [PlayersComponent, PlayerComponent],
  imports: [PlayersRoutingModule, CommonModule],
  providers: [PlayersService, PlayerResolve]
})
export class PlayersModule {}
