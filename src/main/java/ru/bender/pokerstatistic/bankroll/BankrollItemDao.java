package ru.bender.pokerstatistic.bankroll;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

interface BankrollItemDao extends CrudRepository<BankrollItem, Integer> {

    BankrollItem findFirstByOrderByDateTimeDesc();

    BankrollItem findFirstByDateTimeBeforeOrderByDateTimeDesc(LocalDateTime date);

}
