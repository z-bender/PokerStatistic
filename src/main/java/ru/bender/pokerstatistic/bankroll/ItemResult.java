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

}
