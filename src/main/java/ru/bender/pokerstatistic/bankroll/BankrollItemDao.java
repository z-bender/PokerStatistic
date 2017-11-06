package ru.bender.pokerstatistic.bankroll;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

interface BankrollItemDao extends JpaRepository<BankrollItem, Integer> {

    BankrollItem findFirstByOrderByDateTimeDesc();

    BankrollItem findFirstByDateTimeBeforeOrderByDateTimeDesc(LocalDateTime date);

    List<BankrollItem> findAllByDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
