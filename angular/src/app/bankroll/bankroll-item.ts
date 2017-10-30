export class BankrollItem {
  public id: number;
  public dateTime: Date;
  money: number;
  public points: number;
  public type: ItemType;
  public comment: string;
}

export enum ItemType {
  GAME = 'GAME',
  DEPOSIT = 'DEPOSIT',
  WITHDRAWAL = 'WITHDRAWAL',
  CONVERT_BONUS = 'CONVERT_BONUS',
  OTHER = 'OTHER'
}

export enum ItemTypeTranslator {
  GAME = 'Игра',
  DEPOSIT = 'Депозит',
  WITHDRAWAL = 'Вывод',
  CONVERT_BONUS = 'Бонусы',
  OTHER = 'Другое'
}

