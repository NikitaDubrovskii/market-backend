package dev.dubrovsky.marketbackend.repositories;

import dev.dubrovsky.marketbackend.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
