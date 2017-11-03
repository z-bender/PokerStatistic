package ru.bender.pokerstatistic.bankroll;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ItemResult implements Serializable {

    private Integer id;
    private LocalDate date;
    private Integer moneyDifference;
    private Integer pointsDifference;
    private Type type;
    private String comment;

    public ItemResult(BankrollItem bankrollItem, Integer moneyDifference, Integer pointsDifference) {
        this.id = bankrollItem.getId();
        this.date = bankrollItem.getDateTime().toLocalDate();
        this.moneyDifference = moneyDifference;
        this.pointsDifference = pointsDifference;
        this.type = bankrollItem.getType();
        this.comment = bankrollItem.getComment();
    }

}
