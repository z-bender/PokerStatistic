import {Player} from './player';

export class PlayersService {

  private players: Player[];

  constructor() {
    this.players = [
      new Player(1, 'Федор', 'Россия'),
      new Player(2, 'Жан', 'Франция'),
      new Player(3, 'Олексей', 'Украина'),
    ];
  }

  getAll(): Player[] {
    return this.players;
  }

  get(id: number): Player {

    return this.players[id - 1];

    // return new Player(0, 'Дефолт', 'Дефолт');
  }

}
