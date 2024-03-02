package dev.dubrovsky.marketbackend.repositories;

import dev.dubrovsky.marketbackend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByGameId(Long gameId);

}
