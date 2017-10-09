package ru.bender.pokerstatistic.dao;

import org.springframework.data.repository.CrudRepository;
import ru.bender.pokerstatistic.domen.Player;

public interface PlayerDao extends CrudRepository<Player, Integer> {
}
