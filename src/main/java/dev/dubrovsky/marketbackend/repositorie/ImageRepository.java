package dev.dubrovsky.marketbackend.repositorie;

import dev.dubrovsky.marketbackend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
