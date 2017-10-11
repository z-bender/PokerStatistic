package ru.bender.pokerstatistic.players;

import org.springframework.data.repository.CrudRepository;

public interface PlayerDao extends CrudRepository<Player, Integer> {
}
