package ru.bender.pokerstatistic.bankroll;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BankrollItemDto implements Serializable {

    private Integer id;
    private LocalDateTime dateTime;
    private Integer money;
    private Integer points;
    private String  type;
    private String comment;

}
