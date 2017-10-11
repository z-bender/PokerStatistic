package ru.bender.pokerstatistic.bankroll;

import org.springframework.data.repository.CrudRepository;

public interface BankrollItemDao extends CrudRepository<BankrollItem, Integer> {
}
