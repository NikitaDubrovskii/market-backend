package dev.dubrovsky.marketbackend.controller;

import dev.dubrovsky.marketbackend.model.Category;
import dev.dubrovsky.marketbackend.payload.category.NewCategoryPayload;
import dev.dubrovsky.marketbackend.payload.category.UpdateCategoryPayload;
import dev.dubrovsky.marketbackend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("category")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4242"})
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") Long id, Locale locale) {
        var category = categoryService.getById(id, locale);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAll() {
        var all = categoryService.getAll();
        return ResponseEntity.ok().body(all);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @ModelAttribute NewCategoryPayload categoryPayload,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        } else {
            var category = categoryService.add(categoryPayload);
            return ResponseEntity.ok().body(category);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @Valid @ModelAttribute UpdateCategoryPayload categoryPayload,
                                    BindingResult bindingResult,
                                    Locale locale) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        } else {
            var category = categoryService.update(id, categoryPayload, locale);
            return ResponseEntity.ok().body(category);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id,
                                       Locale locale) {
        categoryService.delete(id, locale);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idCategory}/assign")
    public ResponseEntity<Void> assignGamesToCategory(@PathVariable("idCategory") Long idCategory,
                                                      Locale locale,
                                                      @RequestParam Long... idGames) {
        categoryService.assignGamesToCategory(idCategory, locale, idGames);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idCategory}/remove")
    public ResponseEntity<Void> removeGamesFromCategory(@PathVariable("idCategory") Long idCategory,
                                                        Locale locale,
                                                        @RequestParam Long... idGames) {
        categoryService.removeGamesFromCategory(idCategory, locale, idGames);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok().body(categoryService.getCount());
    }

}
