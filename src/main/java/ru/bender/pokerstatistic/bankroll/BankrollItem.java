package ru.bender.pokerstatistic.bankroll;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class BankrollItem implements Serializable {

    // todo: сиквенсы? плюс в других энтити
    @Id
    private Integer id;
    @Column(nullable = false)
    private LocalDateTime dateTime;
    @Column(nullable = false)
    private Integer money;
    @Column(nullable = false)
    private Integer points;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column
    private String comment;
    // TODO: gameTypes

    public BankrollItem() {
    }

    private BankrollItem(LocalDateTime dateTime, Integer money, Integer points, Type type, String comment) {
        this.dateTime = dateTime;
        this.money = money;
        this.points = points;
        this.type = type;
        this.comment = comment;
    }

    public static BankrollItem newItem(LocalDateTime dateTime, Integer money, Integer points,
                                       Type type, String comment) {
        return new BankrollItem(dateTime, money, points, type, comment);
    }

    @Getter
    public enum Type {
        GAME("Игра"),
        DEPOSIT("Депозит"),
        WITHDRAWAL("Вывод средств"),
        CONVERT_BONUS("Бонусы"),
        OTHER("Другое");

        private final String description;

        Type(String description) {
            this.description = description;
        }
    }

}
