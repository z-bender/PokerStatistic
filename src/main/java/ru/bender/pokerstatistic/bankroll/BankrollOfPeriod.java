package ru.bender.pokerstatistic.bankroll;

import java.util.Collections;
import java.util.List;

public class BankrollOfPeriod {

    private List<BankrollItem> sortedByDateItems;
    private DatePeriod period;

    private PeriodResult getPeriodResult() {
        return null;
    }

    public List<BankrollItem> getItems() {
        return Collections.unmodifiableList(sortedByDateItems);
    }

}
