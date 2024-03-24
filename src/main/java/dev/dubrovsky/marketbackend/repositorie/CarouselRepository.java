package dev.dubrovsky.marketbackend.repositorie;

import dev.dubrovsky.marketbackend.model.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselRepository extends JpaRepository<Carousel, Long> {
}
