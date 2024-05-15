package dev.dubrovsky.marketbackend.service;

import dev.dubrovsky.marketbackend.model.Category;
import dev.dubrovsky.marketbackend.model.Game;
import dev.dubrovsky.marketbackend.payload.category.NewCategoryPayload;
import dev.dubrovsky.marketbackend.payload.category.UpdateCategoryPayload;
import dev.dubrovsky.marketbackend.repository.CategoryRepository;
import dev.dubrovsky.marketbackend.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final GameRepository gameRepository;
    private final MessageSource messageSource;

    public Category getById(Long id, Locale locale) {
        return findCategory(id, locale);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category add(NewCategoryPayload categoryPayload) {
        var category = new Category();
        category.setName(categoryPayload.name());
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long id, UpdateCategoryPayload categoryPayload, Locale locale) {
        var categoryOld = findCategory(id, locale);
        if (categoryOld != null) {
            BeanUtils.copyProperties(categoryPayload, categoryOld, "categoryId", "games");
            return categoryRepository.save(categoryOld);
        }
        return null;
    }

    @Transactional
    public void delete(Long id, Locale locale) {
        var category = findCategory(id, locale);

        category.setGames(new HashSet<>());
        categoryRepository.save(category);

        categoryRepository.delete(category);
    }

    @Transactional
    public void assignGamesToCategory(Long idCategory, Locale locale, Long... idGames) {
        var category = findCategory(idCategory, locale);

        Set<Game> gamesFromCategory = category.getGames();
        Set<Game> games = new HashSet<>();
        for (var id : idGames) {
            var game = findGame(id, locale);
            games.add(game);
        }

        gamesFromCategory.addAll(games);

        var temp = new Category();
        BeanUtils.copyProperties(category, temp);
        temp.setGames(gamesFromCategory);
        BeanUtils.copyProperties(temp, category);

        categoryRepository.save(category);
    }

    @Transactional
    public void removeGamesFromCategory(Long idCategory, Locale locale, Long... idGames) {
        var category = findCategory(idCategory, locale);

        Set<Game> gamesFromCategory = category.getGames();
        Set<Game> games = new HashSet<>();
        for (var id : idGames) {
            var game = findGame(id, locale);
            games.add(game);
        }

        gamesFromCategory.removeAll(games);

        var temp = new Category();
        BeanUtils.copyProperties(category, temp);
        temp.setGames(gamesFromCategory);
        BeanUtils.copyProperties(temp, category);

        categoryRepository.save(category);
    }

    public Integer getCount() {
        return categoryRepository.findAll().size();
    }

    private Category findCategory(Long id, Locale locale) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(messageSource.getMessage(
                        "category.errors.category_not_found",
                        new Object[]{id},
                        "Category with id=" + id + " not found",
                        locale
                ))
        );
    }

    private Game findGame(Long id, Locale locale) {
        return gameRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(messageSource.getMessage(
                        "game.errors.game_not_found",
                        new Object[]{id},
                        "Game with id=" + id + " not found",
                        locale
                ))
        );
    }

}
