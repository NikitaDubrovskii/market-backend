package dev.dubrovsky.marketbackend.repositories;

import dev.dubrovsky.marketbackend.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
