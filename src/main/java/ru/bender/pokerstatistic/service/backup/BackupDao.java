package ru.bender.pokerstatistic.service.backup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface BackupDao extends JpaRepository<BackupItem, Integer> {

    @Query(value = "SCRIPT TO ?1", nativeQuery = true)
    List<String> backup(String filename);

}
