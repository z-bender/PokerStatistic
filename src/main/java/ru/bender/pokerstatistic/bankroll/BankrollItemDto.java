package ru.bender.pokerstatistic.bankroll;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BankrollItemDto implements Serializable {

    Integer id;
    LocalDateTime dateTime;
    Integer money;
    Integer points;
    String  type;
    String comment;

}
