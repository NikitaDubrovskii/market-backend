package dev.dubrovsky.marketbackend.controllers;

import dev.dubrovsky.marketbackend.models.Shop;
import dev.dubrovsky.marketbackend.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping()
    public List<Shop> getAll() {
        return shopService.getAll();
    }

    @GetMapping("{id}")
    public Shop getById(@PathVariable("id") Long id) {
        return shopService.getById(id);
    }

    @PostMapping("/add")
    public Shop add(@ModelAttribute Shop shop) {
        return shopService.add(shop);
    }

    @PutMapping("/update/{id}")
    public Shop update(@PathVariable("id") Long id, @ModelAttribute Shop shopNew) {
        return shopService.update(id, shopNew);
    }

    @DeleteMapping("delete/{id}")
    public Shop delete(@PathVariable("id") Long id) {
        return shopService.delete(id);
    }

}
