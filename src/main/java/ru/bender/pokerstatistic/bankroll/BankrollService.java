package ru.bender.pokerstatistic.bankroll;

import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;

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
