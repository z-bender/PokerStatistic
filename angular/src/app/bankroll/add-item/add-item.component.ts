import {Component, OnInit} from '@angular/core';
import {BankrollItem, ItemType, ItemTypeTranslator} from '../bankroll-item';
import {BankrollApiService} from '../../_services/BankrollApiService';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  moduleId: module.id,
  selector: 'app-add-item',
  templateUrl: 'add-item.component.html',
  styleUrls: ['../../main.css']
})
// todo: ограничить выбор даты (от последней записи до сегодня)
export class AddItemComponent implements OnInit {

  bankrollItem: BankrollItem;
  lastItem: BankrollItem;
  moneyDifference: number;
  pointsDifference: number;
  addItemForm: FormGroup;
  itemTypes: string[];

  constructor(private bankrollService: BankrollApiService) {
    this.itemTypes = Object.keys(ItemType);
    this.bankrollItem = new BankrollItem();
    this.bankrollItem.type = ItemType.GAME;
    this.bankrollItem.dateTime = new Date();

    this.bankrollService.getLast(
      result => {
        this.lastItem = result;
        this.bankrollItem.money = this.lastItem.money;
        this.bankrollItem.points = this.lastItem.points;
        this.calcMoneyDifference();
        this.calcPointsDifference();
      }
    );
  }

  ngOnInit(): void {
    this.addItemForm = new FormGroup({
      date: new FormControl('', Validators.required),
      money: new FormControl('', Validators.required),
      points: new FormControl('', Validators.required),
      itemType: new FormControl('', Validators.required),
      comment: new FormControl(''),
    });
  }

  add(): void {
    console.log(this.bankrollItem);
    this.bankrollService.add(this.bankrollItem);
    // todo: update lastItem (передавать коллбэк на сервис?)
  }

  calcMoneyDifference() {
    this.moneyDifference = this.bankrollItem.money - this.lastItem.money;
  }

  calcPointsDifference() {
    this.pointsDifference = this.bankrollItem.points - this.lastItem.points;
  }

  getTypeDescription(itemTypeValue: string) {
    return ItemTypeTranslator[itemTypeValue];
  }

}
