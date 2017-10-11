package ru.bender.pokerstatistic.bankroll;

import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BankrollService {

    BankrollItem addItem(LocalDate date, int money, int points, Type type, String comment);

    BankrollItem addItem(Type type, int money, int points, String comment);

    BankrollOfPeriod getPeriodItems(DatePeriod period);

    BankrollItem getLastItem(LocalDateTime dateTime);

}
