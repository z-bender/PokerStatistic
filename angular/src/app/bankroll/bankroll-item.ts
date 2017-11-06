export class BankrollItem {
  id: number;
  dateTime: Date;
  money: number;
  points: number;
  type: ItemType;
  comment: string;
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

