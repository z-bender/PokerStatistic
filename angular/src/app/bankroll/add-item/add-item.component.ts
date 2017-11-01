import {Component, OnInit} from '@angular/core';
import {BankrollItem, ItemType, ItemTypeTranslator} from '../bankroll-item';
import {BankrollApiService} from '../../_services/BankrollApiService';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbDateParserFormatter} from '../../_services/NgbDateParserFormatter';

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
  datepickerValue;
  moneyDifference: number;
  pointsDifference: number;
  addItemForm: FormGroup;
  itemTypes: string[];

  constructor(private bankrollService: BankrollApiService,
              private formBuilder: FormBuilder,
              private ngbDateParser:NgbDateParserFormatter) {
    this.itemTypes = Object.keys(ItemType);
  }

  private createDefaultItem() {
    this.bankrollItem = new BankrollItem();
    this.bankrollItem.type = ItemType.GAME;
  }

  ngOnInit(): void {
    this.buildForm();
    this.createDefaultItem();
    this.bankrollService.getLast().subscribe(result => this.updateLastItem(result));
  }

  add(): void {
    this.bankrollItem.dateTime = this.ngbDateParser.toDate(this.datepickerValue);
    this.bankrollService.add(this.bankrollItem).subscribe(lastItem => this.updateLastItem(lastItem));
  }

  updateLastItem(lastItem: BankrollItem): void {
    this.lastItem = lastItem;
    this.bankrollItem.money = this.lastItem.money;
    this.bankrollItem.points = this.lastItem.points;
    this.calcMoneyDifference();
    this.calcPointsDifference();
  }

  calcMoneyDifference(): void {
    this.moneyDifference = this.bankrollItem.money - this.lastItem.money;
  }

  calcPointsDifference(): void {
    this.pointsDifference = this.bankrollItem.points - this.lastItem.points;
  }

  getTypeDescription(itemTypeValue: string): string {
    return ItemTypeTranslator[itemTypeValue];
  }

  private buildForm() {
    this.addItemForm = this.formBuilder.group({
      date: ['', Validators.required],
      money: ['', Validators.required],
      points: ['', Validators.required],
      itemType: ['', Validators.required],
      comment: [''],
    });
    this.datepickerValue = this.ngbDateParser.fromDate(new Date())
  }

}
