import {Component, OnInit} from '@angular/core';
import {Player} from './player';
import {Router} from '@angular/router';
import {PlayersService} from './PlayersService';

@Component({
  moduleId: module.id,
  selector: 'app-players',
  templateUrl: 'players.component.html',
  styleUrls: ['players.component.css']
})
export class PlayersComponent implements OnInit {

  players: Player[];
  selectedPlayer: Player;

  constructor(private router: Router, private service: PlayersService) {
  }

  onSelect(player: Player) {
    this.selectedPlayer = player;
    this.router.navigate(['players', player.id]);
  }

  isSelected(player: Player) {
    return player === this.selectedPlayer;
  }

  ngOnInit() {
    this.players = this.service.getAll();
  }

}
