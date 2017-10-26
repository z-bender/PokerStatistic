package ru.bender.pokerstatistic.bankroll;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "bankroll")
@Data
class BankrollItem implements Serializable, Comparable<BankrollItem> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
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

    @Override
    public int compareTo(BankrollItem otherItem) {
        return this.dateTime.isAfter(otherItem.dateTime) ? 1 : -1;
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

        public static Type parse(String type) {
            return Type.valueOf(type.toUpperCase());
        }
    }

}
