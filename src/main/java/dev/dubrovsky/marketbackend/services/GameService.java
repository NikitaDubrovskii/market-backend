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
public class GameService {
    private final GameRepository gameRepository;
    private final CategoryRepository categoryRepository;

    public GameService(GameRepository gameRepository, CategoryRepository categoryRepository) {
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
    }

    public Game getById(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    @Transactional
    public Game add(Game game) {
        return gameRepository.save(game);
    }

    @Transactional
    public Game update(Long id, Game gameNew) {
        var gameOld = gameRepository.findById(id).orElse(null);
        if (gameOld != null) {
            BeanUtils.copyProperties(gameNew, gameOld, "gameId", "currency", "rating", "dateAdded", "categories");
            return gameRepository.save(gameOld);
        }
        return null;
    }

    @Transactional
    public Game delete(Long id) {
        var game = gameRepository.findById(id).orElse(null);
        if (game == null) return null;

        game.setCategories(new HashSet<>());
        gameRepository.save(game);

        gameRepository.delete(game);

        return game;
    }

    @Transactional
    public Game assignCategoriesToGame(Long idGame, Long... idCategories) {
        var game = gameRepository.findById(idGame).orElse(null);
        if (game == null) return null;

        Set<Category> categoriesFromGame = game.getCategories();
        Set<Category> categories = new HashSet<>();
        for (var id : idCategories) {
            var category = categoryRepository.findById(id).orElse(null);
            categories.add(category);
        }

        categoriesFromGame.addAll(categories);

        var temp = new Game();
        BeanUtils.copyProperties(game, temp);
        temp.setCategories(categoriesFromGame);
        BeanUtils.copyProperties(temp, game);

        gameRepository.save(game);
        return game;
    }

    @Transactional
    public Game removeCategoriesFromGame(Long idGame, Long... idCategories) {
        var game = gameRepository.findById(idGame).orElse(null);
        if (game == null) return null;

        Set<Category> categoriesFromGame = game.getCategories();
        Set<Category> categories = new HashSet<>();
        for (var id : idCategories) {
            var category = categoryRepository.findById(id).orElse(null);
            categories.add(category);
        }

        categoriesFromGame.removeAll(categories);

        var temp = new Game();
        BeanUtils.copyProperties(game, temp);
        temp.setCategories(categoriesFromGame);
        BeanUtils.copyProperties(temp, game);

        gameRepository.save(game);
        return game;
    }

}
