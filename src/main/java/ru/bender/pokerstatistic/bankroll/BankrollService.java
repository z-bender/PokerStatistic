package ru.bender.pokerstatistic.bankroll;

import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;

import java.time.LocalDate;

public interface BankrollService {

    BankrollItem addItemForDate(LocalDate date, int money, int points, Type type, String comment);

    BankrollItem addItem(Type type, int money, int points, String comment);

    BankrollOfPeriod getPeriodItems(DatePeriod period);

    BankrollItem getLastItemByDate(LocalDate date);

    BankrollItem getLastItem();

}
