package dev.dubrovsky.marketbackend.services;

import dev.dubrovsky.marketbackend.models.Carousel;
import dev.dubrovsky.marketbackend.repositories.CarouselRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CarouselService {

    private final CarouselRepository carouselRepository;

    public CarouselService(CarouselRepository carouselRepository) {
        this.carouselRepository = carouselRepository;
    }

    public Carousel getById(Long id) {
        return carouselRepository.findById(id).orElse(null);
    }

    public List<Carousel> getAll() {
        return carouselRepository.findAll();
    }

    @Transactional
    public Carousel add(Carousel carousel, MultipartFile file) {
        carousel.setImageName(file.getOriginalFilename());
        try {
            carousel.setImageBytes(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return carouselRepository.save(carousel);
    }

    @Transactional
    public Carousel update(Long id, Carousel carouselNew) {
        var carouselOld = carouselRepository.findById(id).orElse(null);
        if (carouselOld != null) {
            BeanUtils.copyProperties(carouselNew, carouselOld, "carouselId");
            return carouselRepository.save(carouselOld);
        }
        return null;
    }

    @Transactional
    public Carousel delete(Long id) {
        var carousel = carouselRepository.findById(id).orElse(null);
        if (carousel == null) return null;
        carouselRepository.delete(carousel);
        return carousel;
    }

}
