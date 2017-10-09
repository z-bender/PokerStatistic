package ru.bender.pokerstatistic.service;

import ru.bender.pokerstatistic.domen.BankrollItem;
import ru.bender.pokerstatistic.domen.BankrollItem.Type;
import ru.bender.pokerstatistic.dto.DatePeriod;
import ru.bender.pokerstatistic.dto.PeriodResult;

import java.time.LocalDateTime;
import java.util.List;

public interface BankrollService {

    void addItem(LocalDateTime date, Type type, int money, int points, String comment);

    // todo: сделать враппер для возвращаемого листа, который будет возвращать статистику и прочее
    List<BankrollItem> getPeriodItems(DatePeriod period);

    // todo: move to the wrapper
    PeriodResult getPeriodResult(DatePeriod period);

    BankrollItem getLastItem(LocalDateTime dateTime);

}
