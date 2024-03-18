package dev.dubrovsky.marketbackend.services;

import dev.dubrovsky.marketbackend.models.Category;
import dev.dubrovsky.marketbackend.models.Shop;
import dev.dubrovsky.marketbackend.repositories.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    public Shop getById(Long id) {
        return shopRepository.findById(id).orElse(null);
    }

    public List<Shop> getAll() {
        return shopRepository.findAll();
    }

    @Transactional
    public Shop add(Shop shop) {
        return shopRepository.save(shop);
    }

    @Transactional
    public Shop update(Long id, Shop shopNew) {
        var shopOld = shopRepository.findById(id).orElse(null);
        if (shopOld != null) {
            BeanUtils.copyProperties(shopNew, shopOld, "shopId", "shopGames");
            return shopRepository.save(shopOld);
        }
        return null;
    }

    @Transactional
    public Shop delete(Long id) {
        var shop = shopRepository.findById(id).orElse(null);
        if (shop == null) return null;

        shop.setShopGames(new HashSet<>());
        shopRepository.save(shop);

        shopRepository.delete(shop);
        return shop;
    }
}
