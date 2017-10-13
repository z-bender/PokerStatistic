package ru.bender.pokerstatistic.bankroll;

import com.sun.istack.internal.Nullable;
import ru.bender.pokerstatistic.utils.DatePeriod;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

class BankrollOfPeriod {

    private final List<BankrollItem> sortedByDateItems;
    private final DatePeriod period;
    private final Integer initialMoney;
    private final Integer initialPoints;

    public BankrollOfPeriod(List<BankrollItem> bankrollItems, @Nullable BankrollItem lastItem, DatePeriod period) {
        Collections.sort(bankrollItems);
        this.sortedByDateItems = Collections.unmodifiableList(bankrollItems);
        this.initialMoney = nonNull(lastItem) ? lastItem.getMoney() : bankrollItems.get(0).getMoney();
        this.initialPoints = nonNull(lastItem) ? lastItem.getPoints() : bankrollItems.get(0).getPoints();
        this.period = period;
    }

    private PeriodResult getPeriodResult() {
        return null;
    }

    public List<BankrollItem> getItems() {
        return sortedByDateItems;
    }

}
