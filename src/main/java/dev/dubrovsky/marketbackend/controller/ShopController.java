package dev.dubrovsky.marketbackend.controller;

import dev.dubrovsky.marketbackend.model.Shop;
import dev.dubrovsky.marketbackend.payload.shop.NewShopPayload;
import dev.dubrovsky.marketbackend.payload.shop.UpdateShopPayload;
import dev.dubrovsky.marketbackend.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("shop")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4242"})
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping()
    public ResponseEntity<List<Shop>> getAll() {
        var all = shopService.getAll();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("{id}")
    public ResponseEntity<Shop> getById(@PathVariable("id") Long id,
                                        Locale locale) {
        var shop = shopService.getById(id, locale);
        return ResponseEntity.ok().body(shop);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @ModelAttribute NewShopPayload shopPayload,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        } else {
            var shop = shopService.add(shopPayload);
            return ResponseEntity.ok().body(shop);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @Valid @ModelAttribute UpdateShopPayload shopPayload,
                                    BindingResult bindingResult,
                                    Locale locale) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        } else {
            var shop = shopService.update(id, shopPayload, locale);
            return ResponseEntity.ok().body(shop);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id,
                                       Locale locale) {
        shopService.delete(id, locale);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok().body(shopService.getCount());
    }
}
