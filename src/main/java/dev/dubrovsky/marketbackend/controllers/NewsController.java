package dev.dubrovsky.marketbackend.controllers;

import dev.dubrovsky.marketbackend.models.News;
import dev.dubrovsky.marketbackend.services.NewsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("news")
@CrossOrigin(origins = "http://localhost:4200")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{id}")
    public News getById(@PathVariable(name = "id") Long id) {
        return newsService.getById(id);
    }

    @GetMapping()
    public List<News> getAll() {
        return newsService.getAll();
    }

    @PostMapping("/add")
    public News add(@ModelAttribute News news) {
        return newsService.add(news);
    }

    @PutMapping("/update/{id}")
    public News update(@PathVariable("id") Long id, @ModelAttribute News news) {
        return newsService.update(id, news);
    }

    @DeleteMapping("/delete/{id}")
    public News delete(@PathVariable("id") Long id) {
        return newsService.delete(id);
    }

}
