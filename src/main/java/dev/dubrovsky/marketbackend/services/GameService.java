package dev.dubrovsky.marketbackend.services;

import dev.dubrovsky.marketbackend.dto.GameNewDTO;
import dev.dubrovsky.marketbackend.dto.GameOfTheDayDTO;
import dev.dubrovsky.marketbackend.dto.GamePopularDTO;
import dev.dubrovsky.marketbackend.dto.GameSaleDTO;
import dev.dubrovsky.marketbackend.facade.GameFacade;
import dev.dubrovsky.marketbackend.models.Category;
import dev.dubrovsky.marketbackend.models.Game;
import dev.dubrovsky.marketbackend.models.Image;
import dev.dubrovsky.marketbackend.repositories.CategoryRepository;
import dev.dubrovsky.marketbackend.repositories.GameRepository;
import dev.dubrovsky.marketbackend.repositories.ImageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class GameService {
    private final GameRepository gameRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final GameFacade gameFacade;

    public GameService(GameRepository gameRepository,
                       CategoryRepository categoryRepository,
                       ImageRepository imageRepository, GameFacade gameFacade) {
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.gameFacade = gameFacade;
    }

    public Game getById(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    public List<GamePopularDTO> getPopularGame() {
        var all = gameRepository.findAllByOrderByRatingDesc();
        var byGameOfTheDay = gameRepository.findByGameOfTheDay(true);
        var gamePopularDTOS = new ArrayList<GamePopularDTO>();

        if (all.size() != 1) {
            for (Game game : all) {
                if (Objects.equals(game.getGameId(), byGameOfTheDay.getGameId())) {
                    all.remove(game);
                    break;
                }
            }
        }
        var repeatedGame = all.getLast();

        while (all.size() < 4) {
            all.add(repeatedGame);
        }

        for (var i = 0; i < 4; i++) {
            var gamePopularDTO = gameFacade.gameToGamePopularDTO(all.get(i));
            gamePopularDTOS.add(gamePopularDTO);
        }

        return gamePopularDTOS;
    }

    public GameOfTheDayDTO getGameOfTheDay() {
        var gameOfTheDay = gameRepository.findByGameOfTheDay(true);

        return gameFacade.gameToGameOfTheDay(gameOfTheDay);
    }

    public List<GameNewDTO> getNewGame() {
        var all = gameRepository.findAllByOrderByDateAddedAsc();
        var gameNewDTOS = new ArrayList<GameNewDTO>();

        for (var i = 0; i < 3; i++) {
            var gameNewDTO = gameFacade.gameToGameNewDTO(all.get(i));
            gameNewDTOS.add(gameNewDTO);
        }

        return gameNewDTOS.reversed();
    }

    public List<GameSaleDTO> getSaleGame() {
        var all = gameRepository.findAllBySale(true);
        var gameSaleDTOS = new ArrayList<GameSaleDTO>();

        for (var i = 0; i < 3; i++) {
            var gameSaleDTO = gameFacade.gameToGameSaleDTO(all.get(i));
            gameSaleDTOS.add(gameSaleDTO);
        }

        return gameSaleDTOS;
    }

    @Transactional
    public Game add(Game game, MultipartFile... files) {
        game.setDateAdded(LocalDate.now());
        game.setCurrency("USD");
        var gameSave = gameRepository.save(game);

        var firstImg = true;
        try {
            for (var file : files) {
                var image = new Image();
                if (firstImg) {
                    image.setPreview(true);
                    firstImg = false;
                }
                image.setName(file.getOriginalFilename());
                image.setGameId(gameSave.getGameId());
                image.setImageBytes(file.getBytes());

                imageRepository.save(image);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gameRepository.save(gameSave);

        return gameSave;
    }

    @Transactional
    public Game addGameOfTheDay(Long gameId, Integer discount) {
        var gameOfTheDay = gameRepository.findByGameOfTheDay(true);
        if (gameOfTheDay != null) {
            gameOfTheDay.setGameOfTheDay(false);
            gameOfTheDay.setDiscount(null);
            gameOfTheDay.setPriceWithDiscount(null);
            gameRepository.save(gameOfTheDay);
        }

        var game = gameRepository.findById(gameId).orElse(null);
        if (game == null) return null;

        var price = game.getPrice();
        var priceWithDiscount = price
                .multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf((double) discount / 100)))
                .setScale(2, RoundingMode.HALF_UP);

        game.setGameOfTheDay(true);
        game.setDiscount(discount);
        game.setPriceWithDiscount(priceWithDiscount);

        gameRepository.save(game);
        return game;
    }

    @Transactional
    public List<Game> addGameSale(Integer discount, Long... gameIds) {
        var games = new ArrayList<Game>();
        for (var gameId : gameIds) {
            var game = gameRepository.findById(gameId).orElse(null);
            if (game == null) continue;

            var price = game.getPrice();
            var priceWithDiscount = price
                    .multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf((double) discount / 100)))
                    .setScale(2, RoundingMode.HALF_UP);

            game.setSale(true);
            game.setDiscount(discount);
            game.setPriceWithDiscount(priceWithDiscount);

            gameRepository.save(game);
            games.add(game);
        }

        return games;
    }

    @Transactional
    public Game update(Long id, Game gameNew) {
        var gameOld = gameRepository.findById(id).orElse(null);
        if (gameOld != null) {
            BeanUtils.copyProperties(gameNew, gameOld, "gameId",
                    "currency", "rating", "dateAdded", "categories");
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
    public void removeGameSale(Long... gameIds) {
        var all = gameRepository.findAllBySale(true);
        for (var game : all) {
            game.setSale(false);
            game.setDiscount(null);
            game.setPriceWithDiscount(null);

            gameRepository.save(game);
        }
    }

    @Transactional
    public Game assignCategoriesToGame(Long idGame, Long... idCategories) {
        var game = gameRepository.findById(idGame).orElse(null);
        if (game == null) return null;

        var categoriesFromGame = game.getCategories();
        var categories = new HashSet<Category>();
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

        var categoriesFromGame = game.getCategories();
        var categories = new HashSet<Category>();
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
