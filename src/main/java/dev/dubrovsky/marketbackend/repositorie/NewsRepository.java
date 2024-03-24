package dev.dubrovsky.marketbackend.repositorie;

import dev.dubrovsky.marketbackend.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
