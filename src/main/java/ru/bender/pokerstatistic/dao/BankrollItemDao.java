package ru.bender.pokerstatistic.dao;

import org.springframework.data.repository.CrudRepository;
import ru.bender.pokerstatistic.domen.BankrollItem;

public interface BankrollItemDao extends CrudRepository<BankrollItem, Integer> {
}
