package ru.bender.pokerstatistic.bankroll;

import lombok.Data;
import ru.bender.pokerstatistic.utils.DatePeriod;

import java.io.Serializable;

@Data
class PeriodResult implements Serializable {

    private Integer moneyAtEnd;
    private Integer winning;
    private Integer deposit;
    private Integer bonus;
    private Integer otherChanges;
    private Integer withdrawal;
    private Integer pointsAtEnd;
    private Integer earnedPoints;
    private Integer spentPoints;
    private DatePeriod period;

}
