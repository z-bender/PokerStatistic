package ru.bender.pokerstatistic.bankroll;

import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;
import ru.bender.pokerstatistic.utils.DatePeriod;

import java.time.LocalDate;
import java.time.LocalTime;

interface BankrollService {

    LocalTime TIME_FOR_NEW_ITEM_FROM_PAST_DAY = LocalTime.of(12, 0);

    BankrollItem addItem(LocalDate date, int money, int points, Type type, String comment)
    throws ExistFutureItemException, AddItemInFutureException;

    PeriodResultWithItems getMonthResults(Integer year, Integer month);

    BankrollOfPeriod getBankrollOfPeriod(DatePeriod period);

    ParentChildPeriodResults getAllPeriodResults();

    BankrollItem getLastItemByDate(LocalDate date);

    BankrollItem getLastItem();

    BankrollItemDto mapToDto(BankrollItem item);

}
