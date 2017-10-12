package ru.bender.pokerstatistic.players;

import org.springframework.data.repository.CrudRepository;

interface PlayerDao extends CrudRepository<Player, Integer> {
}
