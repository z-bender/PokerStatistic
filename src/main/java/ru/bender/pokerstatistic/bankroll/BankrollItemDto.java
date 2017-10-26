package ru.bender.pokerstatistic.bankroll;

import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
public class BankrollItemDto implements Serializable {

    Integer id;
    LocalDateTime dateTime;
    Integer money;
    Integer points;
    String  type;
    String comment;

}
