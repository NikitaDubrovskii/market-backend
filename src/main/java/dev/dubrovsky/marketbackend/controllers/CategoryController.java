package dev.dubrovsky.marketbackend.controllers;

import dev.dubrovsky.marketbackend.models.Category;
import dev.dubrovsky.marketbackend.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public Category getById(@PathVariable("id") Long id) {
        return categoryService.getById(id);
    }

    @GetMapping()
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @PostMapping("/add")
    public Category add(@RequestBody Category category) {
        return categoryService.add(category);
    }

    @PutMapping("/update/{id}")
    public Category update(@PathVariable("id") Long id, @RequestBody Category categoryNew) {
        return categoryService.update(id, categoryNew);
    }

    @DeleteMapping("/delete/{id}")
    public Category delete(@PathVariable("id") Long id) {
        return categoryService.delete(id);
    }

    @PutMapping("/{idCategory}/assign")
    public Category assignGamesToCategory(@PathVariable("idCategory") Long idCategory, @RequestParam Long... idGames) {
        return categoryService.assignGamesToCategory(idCategory, idGames);
    }

    @PutMapping("/{idCategory}/remove")
    public Category removeGamesFromCategory(@PathVariable("idCategory") Long idCategory, @RequestParam Long... idGames) {
        return categoryService.removeGamesFromCategory(idCategory, idGames);
    }

}
