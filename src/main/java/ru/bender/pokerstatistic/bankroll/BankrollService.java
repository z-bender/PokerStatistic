package ru.bender.pokerstatistic.bankroll;

import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;

import java.time.LocalDateTime;

public interface BankrollService {

    void addItem(LocalDateTime date, Type type, int money, int points, String comment);

    BankrollOfPeriod getPeriodItems(DatePeriod period);

    BankrollItem getLastItem(LocalDateTime dateTime);

}
