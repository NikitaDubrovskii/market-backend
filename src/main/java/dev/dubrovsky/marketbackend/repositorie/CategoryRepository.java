package dev.dubrovsky.marketbackend.repositorie;

import dev.dubrovsky.marketbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
