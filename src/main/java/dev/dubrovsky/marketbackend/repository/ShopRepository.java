package dev.dubrovsky.marketbackend.repository;

import dev.dubrovsky.marketbackend.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
