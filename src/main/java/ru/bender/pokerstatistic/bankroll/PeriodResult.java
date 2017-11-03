package ru.bender.pokerstatistic.bankroll;

import lombok.Data;
import ru.bender.pokerstatistic.utils.DatePeriod;

import java.io.Serializable;

@Data
class PeriodResult implements Serializable {

    Integer moneyAtEnd;
    Integer winning;
    Integer deposit;
    Integer bonus;
    Integer otherChanges;
    Integer withdrawal;
    Integer pointsAtEnd;
    Integer earnedPoints;
    Integer spentPoints;
    DatePeriod period;

    public PeriodResult(DatePeriod period) {
        this.period = period;
        this.moneyAtEnd = 0;
        this.winning = 0;
        this.deposit = 0;
        this.bonus = 0;
        this.otherChanges = 0;
        this.withdrawal = 0;
        this.pointsAtEnd = 0;
        this.earnedPoints = 0;
        this.spentPoints = 0;
    }

    public void calculateItemResult(ItemResult itemResult) {
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

}
