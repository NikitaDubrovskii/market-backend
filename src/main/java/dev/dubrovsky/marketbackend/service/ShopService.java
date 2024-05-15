package dev.dubrovsky.marketbackend.service;

import dev.dubrovsky.marketbackend.model.Shop;
import dev.dubrovsky.marketbackend.payload.shop.NewShopPayload;
import dev.dubrovsky.marketbackend.payload.shop.UpdateShopPayload;
import dev.dubrovsky.marketbackend.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final MessageSource messageSource;

    public Shop getById(Long id, Locale locale) {
        return findShop(id, locale);
    }

    public List<Shop> getAll() {
        return shopRepository.findAll();
    }

    @Transactional
    public Shop add(NewShopPayload shopPayload) {
        var shop = new Shop();
        shop.setName(shopPayload.name());
        shop.setAddress(shopPayload.address());
        shop.setPhone(shopPayload.phone());
        shop.setInfo(shopPayload.info());
        return shopRepository.save(shop);
    }

    @Transactional
    public Shop update(Long id, UpdateShopPayload shopPayload, Locale locale) {
        var shopOld = findShop(id, locale);
        if (shopOld != null) {
            BeanUtils.copyProperties(shopPayload, shopOld, "shopId", "shopGames");
            return shopRepository.save(shopOld);
        }
        return null;
    }

    @Transactional
    public void delete(Long id, Locale locale) {
        var shop = findShop(id, locale);

        shop.setShopGames(new HashSet<>());
        shopRepository.save(shop);

        shopRepository.delete(shop);
    }

    public Integer getCount() {
        return shopRepository.findAll().size();
    }

    private Shop findShop(Long id, Locale locale) {
        return shopRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(messageSource.getMessage(
                        "carousel.errors.carousel_not_found",
                        new Object[]{id},
                        "Carousel with id=" + id + " not found.",
                        locale
                ))
        );
    }
}
