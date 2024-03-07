package dev.dubrovsky.marketbackend.controllers;

import dev.dubrovsky.marketbackend.models.Carousel;
import dev.dubrovsky.marketbackend.services.CarouselService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/carousel")
@CrossOrigin(origins = "http://localhost:4200")
public class CarouselController {

    private final CarouselService carouselService;

    public CarouselController(CarouselService carouselService) {
        this.carouselService = carouselService;
    }

    @GetMapping("/{id}")
    public Carousel getById(@PathVariable(name = "id") Long id) {
        return carouselService.getById(id);
    }

    @GetMapping()
    public List<Carousel> getAll() {
        return carouselService.getAll();
    }

    @PostMapping("/add")
    public Carousel add(@ModelAttribute Carousel carousel, @RequestParam("image") MultipartFile file) {
        return carouselService.add(carousel, file);
    }

    @PutMapping("/update/{id}")
    public Carousel update(@PathVariable("id") Long id, @ModelAttribute Carousel carousel) {
        return carouselService.update(id, carousel);
    }

    @DeleteMapping("/delete/{id}")
    public Carousel delete(@PathVariable("id") Long id) {
        return carouselService.delete(id);
    }

}
