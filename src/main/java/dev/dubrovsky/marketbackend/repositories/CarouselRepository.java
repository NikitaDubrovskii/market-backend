package dev.dubrovsky.marketbackend.repositories;

import dev.dubrovsky.marketbackend.models.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselRepository extends JpaRepository<Carousel, Long> {
}
