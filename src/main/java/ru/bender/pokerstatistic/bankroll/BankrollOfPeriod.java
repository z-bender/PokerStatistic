package ru.bender.pokerstatistic.bankroll;

import ru.bender.pokerstatistic.utils.DatePeriod;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

class BankrollOfPeriod {

    private final List<BankrollItem> sortedByDateItems;
    private final DatePeriod period;
    private final Integer initialMoney;
    private final Integer initialPoints;


    public BankrollOfPeriod(List<BankrollItem> bankrollItems, BankrollItem lastItem, DatePeriod period) {
        this.period = period;
        Collections.sort(bankrollItems);
        this.sortedByDateItems = Collections.unmodifiableList(bankrollItems);
        this.initialMoney = nonNull(lastItem) ? lastItem.getMoney() : getFirstItemMoney();
        this.initialPoints = nonNull(lastItem) ? lastItem.getPoints() : getFirstItemPoints();
    }

    public PeriodResultWithItems getPeriodResult() {
        PeriodResultWithItems result = new PeriodResultWithItems(period);
        int previousMoney = initialMoney;
        int previousPoints = initialPoints;
        for (BankrollItem bankrollItem : sortedByDateItems) {
            ItemResult itemResult = bankrollItem.getItemResult(previousMoney, previousPoints);
            result.add(itemResult);
            previousMoney = bankrollItem.getMoney();
            previousPoints = bankrollItem.getPoints();
        }
        return result.finalizeCalculation(previousMoney, previousPoints);
    }

    List<BankrollItem> getItems() {
        return sortedByDateItems;
    }

    private Integer getFirstItemMoney() {
        return sortedByDateItems.isEmpty() ? null : sortedByDateItems.get(0).getMoney();
    }

    private Integer getFirstItemPoints() {
        return sortedByDateItems.isEmpty() ? null : sortedByDateItems.get(0).getPoints();
    }

}
