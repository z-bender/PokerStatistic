import {ItemType} from '../bankroll-item';

export class ItemResult {
  id: number;
  date: string;
  moneyDifference: number;
  pointsDifference: number;
  type: ItemType;
  comment: string;
}
