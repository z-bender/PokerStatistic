package ru.bender.pokerstatistic.domen;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Player implements Serializable {

    @Id
    private Integer id;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private LocalDateTime created;
    @Column(nullable = false)
    private LocalDateTime updated;

}
