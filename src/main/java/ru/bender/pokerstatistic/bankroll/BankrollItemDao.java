package ru.bender.pokerstatistic.bankroll;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

interface BankrollItemDao extends CrudRepository<BankrollItem, Integer> {

    BankrollItem findFirstByOrderByDateTimeDesc();

    BankrollItem findFirstByDateTimeBeforeOrderByDateTimeDesc(LocalDateTime date);

    List<BankrollItem> findAllByDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
