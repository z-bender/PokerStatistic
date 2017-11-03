package ru.bender.pokerstatistic.bankroll;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.bender.pokerstatistic.utils.DatePeriod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
//todo: убрать после реализации PeriodResult
@EqualsAndHashCode(exclude = {"tempItemsResults", "itemsResults"})
class PeriodResultWithItems extends PeriodResult {

    private List<ItemResult> itemsResults;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private List<ItemResult> tempItemsResults;

    public PeriodResultWithItems(DatePeriod period) {
        this.period = period;
        tempItemsResults = new ArrayList<>();
    }

    public void add(ItemResult itemResult) {
        tempItemsResults.add(itemResult);
        calculateItemResult(itemResult);
    }

    private void calculateItemResult(ItemResult itemResult) {
        calculateItemMoney(itemResult);
        calculateItemPoints(itemResult);
    }

    private void calculateItemMoney(ItemResult itemResult) {
        Integer money = itemResult.getMoneyDifference();
        switch (itemResult.getType()) {
            case GAME:
                winning += money;
                break;
            case DEPOSIT:
                deposit += money;
                break;
            case WITHDRAWAL:
                withdrawal += -money;
                break;
            case CONVERT_BONUS:
                bonus += money;
                break;
            case OTHER:
                otherChanges += money;
                break;
        }
    }

    private void calculateItemPoints(ItemResult itemResult) {
        int points = itemResult.getPointsDifference();
        if (points > 0) {
            earnedPoints += points;
        } else {
            spentPoints += -points;
        }
    }

    public PeriodResultWithItems finalizeCalculation(Integer lastMoneyValue, Integer lastPointsValue) {
        moneyAtEnd = lastMoneyValue;
        pointsAtEnd = lastPointsValue;
        itemsResults = Collections.unmodifiableList(tempItemsResults);
        return this;
    }

}
