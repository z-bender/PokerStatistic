package ru.bender.pokerstatistic.service.backup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

interface BackupDao extends JpaRepository<BackupItem, Integer> {

    /**
     * Create backup in sql format to file
     *
     * @param filename path to created file
     * @return sql of backup
     */
    @Query(value = "SCRIPT TO ?1", nativeQuery = true)
    List<String> backup(String filename);

    BackupItem findFirstByDateTimeAfterOrderByDateTimeDesc(LocalDateTime dateTime);

}
