package ru.bender.pokerstatistic.bankroll;

import ru.bender.pokerstatistic.utils.DatePeriod;

import java.util.Collections;
import java.util.List;

class BankrollOfPeriod {

    private List<BankrollItem> sortedByDateItems;
    private DatePeriod period;

    private PeriodResult getPeriodResult() {
        return null;
    }

    public List<BankrollItem> getItems() {
        return Collections.unmodifiableList(sortedByDateItems);
    }

}
