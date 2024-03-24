package dev.dubrovsky.marketbackend.controller;

import dev.dubrovsky.marketbackend.model.Carousel;
import dev.dubrovsky.marketbackend.payload.carousel.NewCarouselPayload;
import dev.dubrovsky.marketbackend.payload.carousel.UpdateCarouselPayload;
import dev.dubrovsky.marketbackend.service.CarouselService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/carousel")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CarouselController {

    private final CarouselService carouselService;

    @GetMapping("/{id}")
    public ResponseEntity<Carousel> getById(@PathVariable(name = "id") Long id,
                                            Locale locale) {
        var carousel = carouselService.getById(id, locale);
        return ResponseEntity.ok().body(carousel);
    }

    @GetMapping()
    public ResponseEntity<List<Carousel>> getAll() {
        var all = carouselService.getAll();
        return ResponseEntity.ok().body(all);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @ModelAttribute NewCarouselPayload carouselPayload,
                                 BindingResult bindingResult,
                                 @RequestParam("image") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        } else {
            var carousel = carouselService.add(carouselPayload, file);
            return ResponseEntity.ok().body(carousel);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @Valid @ModelAttribute UpdateCarouselPayload carouselPayload,
                                    BindingResult bindingResult,
                                    Locale locale) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        } else {
            var carousel = carouselService.update(id, carouselPayload, locale);
            return ResponseEntity.ok().body(carousel);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id,
                                       Locale locale) {
        carouselService.delete(id, locale);
        return ResponseEntity.noContent().build();
    }

}
