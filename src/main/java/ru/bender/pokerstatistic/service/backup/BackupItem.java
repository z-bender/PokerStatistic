package ru.bender.pokerstatistic.service.backup;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;
import static ru.bender.pokerstatistic.utils.Utils.now;

@Entity
// TODO: 18.01.19 rename (name may be reserved)
@Table(name = "backup")
@Data
class BackupItem implements Serializable {

    static BackupItem newInstance(String filename) {
        BackupItem item = new BackupItem();
        item.setFilename(filename);
        return item;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private LocalDateTime dateTime;
    @Column(nullable = false)
    private String filename;
    // TODO: 12.01.19 deleted flag?

    @PrePersist
    public void onPrePersist() {
        dateTime = now();
    }

}
