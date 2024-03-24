package dev.dubrovsky.marketbackend.repositorie;

import dev.dubrovsky.marketbackend.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByGameOfTheDay(boolean gameOfTheDay);

    List<Game> findAllByOrderByRatingDesc();

    List<Game> findAllByOrderByDateAddedAsc();

    List<Game> findAllBySale(boolean sale);

}
