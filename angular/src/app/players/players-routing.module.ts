import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {PlayerComponent} from './player.component';
import {PlayersComponent} from './players.component';
import {PlayerResolve} from './player.resolve';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'players',
        component: PlayersComponent,
        children: [{path: ':id', component: PlayerComponent, resolve: {player: PlayerResolve}}]
      },
    ])
  ],
  exports: [RouterModule]
})
export class PlayersRoutingModule {

}
