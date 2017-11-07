package ru.bender.pokerstatistic.bankroll;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import ru.bender.pokerstatistic.utils.DatePeriod;

@Data
class PeriodResult {
    
    @Setter(AccessLevel.NONE)
    protected DatePeriod period;
    protected String periodName;
    protected Integer moneyAtEnd;
    protected int winning = 0;
    protected int deposit = 0;
    protected int bonus = 0;
    protected int otherChanges = 0;
    protected int withdrawal = 0;
    protected Integer pointsAtEnd;
    protected int earnedPoints = 0;
    protected int spentPoints = 0;

    PeriodResult setYearToPeriodName() {
        periodName = "" + period.start.getYear();
        return this;
    }

    PeriodResult setMonthToPeriodName() {
        periodName = period.start.getMonth().name();
        return this;
    }

}
