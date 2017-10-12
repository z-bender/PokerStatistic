package ru.bender.pokerstatistic.bankroll;

import org.springframework.data.repository.CrudRepository;

interface BankrollItemDao extends CrudRepository<BankrollItem, Integer> {
}
