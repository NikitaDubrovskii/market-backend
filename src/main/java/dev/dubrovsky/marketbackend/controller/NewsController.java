package dev.dubrovsky.marketbackend.controller;

import dev.dubrovsky.marketbackend.model.News;
import dev.dubrovsky.marketbackend.payload.news.NewNewsPayload;
import dev.dubrovsky.marketbackend.payload.news.UpdateNewsPayload;
import dev.dubrovsky.marketbackend.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("news")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4242"})
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/{id}")
    public ResponseEntity<News> getById(@PathVariable(name = "id") Long id,
                                        Locale locale) {
        var news = newsService.getById(id, locale);
        return ResponseEntity.ok().body(news);
    }

    @GetMapping()
    public ResponseEntity<List<News>> getAll() {
        var all = newsService.getAll();
        return ResponseEntity.ok().body(all);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @ModelAttribute NewNewsPayload newsPayload,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        } else {
            var news = newsService.add(newsPayload);
            return ResponseEntity.ok().body(news);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                       @Valid @ModelAttribute UpdateNewsPayload newsPayload,
                       BindingResult bindingResult,
                       Locale locale) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        } else {
            var news = newsService.update(id, newsPayload, locale);
            return ResponseEntity.ok().body(news);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id,
                                       Locale locale) {
        newsService.delete(id, locale);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok().body(newsService.getCount());
    }

}
