package dev.dubrovsky.marketbackend.services;

import dev.dubrovsky.marketbackend.models.Category;
import dev.dubrovsky.marketbackend.models.Game;
import dev.dubrovsky.marketbackend.repositories.CategoryRepository;
import dev.dubrovsky.marketbackend.repositories.GameRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final GameRepository gameRepository;

    public CategoryService(CategoryRepository categoryRepository, GameRepository gameRepository) {
        this.categoryRepository = categoryRepository;
        this.gameRepository = gameRepository;
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long id, Category categoryNew) {
        var categoryOld = categoryRepository.findById(id).orElse(null);
        if (categoryOld != null) {
            BeanUtils.copyProperties(categoryNew, categoryOld, "categoryId", "games");
            return categoryRepository.save(categoryOld);
        }
        return null;
    }

    @Transactional
    public Category delete(Long id) {
        var category = categoryRepository.findById(id).orElse(null);
        if (category == null) return null;

        category.setGames(new HashSet<>());
        categoryRepository.save(category);

        categoryRepository.delete(category);
        return category;
    }

    @Transactional
    public Category assignGamesToCategory(Long idCategory, Long... idGames) {
        var category = categoryRepository.findById(idCategory).orElse(null);
        if (category == null) return null;

        Set<Game> gamesFromCategory = category.getGames();
        Set<Game> games = new HashSet<>();
        for (var id : idGames) {
            var game = gameRepository.findById(id).orElse(null);
            games.add(game);
        }

        gamesFromCategory.addAll(games);

        var temp = new Category();
        BeanUtils.copyProperties(category, temp);
        temp.setGames(gamesFromCategory);
        BeanUtils.copyProperties(temp, category);

        categoryRepository.save(category);
        return category;
    }

    @Transactional
    public Category removeGamesFromCategory(Long idCategory, Long... idGames) {
        var category = categoryRepository.findById(idCategory).orElse(null);
        if (category == null) return null;

        Set<Game> gamesFromCategory = category.getGames();
        Set<Game> games = new HashSet<>();
        for (var id : idGames) {
            var game = gameRepository.findById(id).orElse(null);
            games.add(game);
        }

        gamesFromCategory.removeAll(games);

        var temp = new Category();
        BeanUtils.copyProperties(category, temp);
        temp.setGames(gamesFromCategory);
        BeanUtils.copyProperties(temp, category);

        categoryRepository.save(category);
        return category;
    }

}
