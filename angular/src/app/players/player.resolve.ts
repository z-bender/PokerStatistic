import {Player} from './player';
import {ActivatedRouteSnapshot, Resolve, Router} from '@angular/router';
import {Injectable} from '@angular/core';
import {PlayersService} from './PlayersService';

@Injectable()
export class PlayerResolve implements Resolve<Player> {

  constructor(private service: PlayersService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot): Promise<Player> | Player {
    const id: number = +route.params['id'];
    const player: Player = this.service.get(id);
    if (player) {
      return player;
    } else {
      this.router.navigate(['/players']);
    }
  }
}
