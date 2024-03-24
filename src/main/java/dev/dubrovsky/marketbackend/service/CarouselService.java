package dev.dubrovsky.marketbackend.service;

import dev.dubrovsky.marketbackend.model.Carousel;
import dev.dubrovsky.marketbackend.payload.carousel.NewCarouselPayload;
import dev.dubrovsky.marketbackend.payload.carousel.UpdateCarouselPayload;
import dev.dubrovsky.marketbackend.repositorie.CarouselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarouselService {

    private final CarouselRepository carouselRepository;

    private final MessageSource messageSource;

    public Carousel getById(Long id, Locale locale) {
        return findCarousel(id, locale);
    }

    public List<Carousel> getAll() {
        return carouselRepository.findAll();
    }

    @Transactional
    public Carousel add(NewCarouselPayload carouselPayload, MultipartFile file) {
        var carousel = new Carousel();
        carousel.setTitle(carouselPayload.title());
        carousel.setText(carouselPayload.text());
        carousel.setImageName(file.getOriginalFilename());
        try {                                               //TODO обработка ошибки фотографии
            carousel.setImageBytes(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return carouselRepository.save(carousel);
    }

    @Transactional
    public Carousel update(Long id, UpdateCarouselPayload carouselPayload, Locale locale) {
        var carouselOld = findCarousel(id, locale);
        if (carouselOld != null) {
            BeanUtils.copyProperties(carouselPayload, carouselOld, "carouselId");
            return carouselRepository.save(carouselOld);
        }
        return null;
    }

    @Transactional
    public void delete(Long id, Locale locale) {
        var carousel = findCarousel(id, locale);
        carouselRepository.delete(carousel);
    }

    private Carousel findCarousel(Long id, Locale locale) {
        return carouselRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(messageSource.getMessage(
                        "carousel.errors.carousel_not_found",
                        new Object[]{id},
                        "Carousel with id=" + id + " not found.",
                        locale
                ))
        );
    }

}
