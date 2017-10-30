import {Component, OnInit} from '@angular/core';
import {Player} from './player';
import {ActivatedRoute} from '@angular/router';
import {PlayersService} from './PlayersService';

@Component({
  moduleId: module.id,
  selector: 'app-player',
  template: '{{player.name}}<br />{{player.country}}'
})
export class PlayerComponent implements OnInit {
  player: Player;


  constructor(private activatedRoute: ActivatedRoute, private service: PlayersService) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.forEach(
      (data: {player: Player}) => {
        // console.log('id - ' + params['id']);
        this.player = data.player;
      }
    );
  }
}
