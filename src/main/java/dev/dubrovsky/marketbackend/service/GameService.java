package dev.dubrovsky.marketbackend.service;

import dev.dubrovsky.marketbackend.dto.GameNewDTO;
import dev.dubrovsky.marketbackend.dto.GameOfTheDayDTO;
import dev.dubrovsky.marketbackend.dto.GamePopularDTO;
import dev.dubrovsky.marketbackend.dto.GameSaleDTO;
import dev.dubrovsky.marketbackend.facade.GameFacade;
import dev.dubrovsky.marketbackend.model.Category;
import dev.dubrovsky.marketbackend.model.Game;
import dev.dubrovsky.marketbackend.model.Image;
import dev.dubrovsky.marketbackend.payload.game.NewGamePayload;
import dev.dubrovsky.marketbackend.payload.game.UpdateGamePayload;
import dev.dubrovsky.marketbackend.repository.CategoryRepository;
import dev.dubrovsky.marketbackend.repository.GameRepository;
import dev.dubrovsky.marketbackend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final GameFacade gameFacade;

    private final MessageSource messageSource;

    public Game getById(Long id, Locale locale) {
        return findGame(id, locale);
    }

    public List<Game> getAll() {
        return gameRepository.findAll();
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
    public Game add(NewGamePayload gamePayload, Locale locale, MultipartFile... files) {
        var game = new Game();
        game.setName(gamePayload.name());
        game.setDescription(gamePayload.description());
        game.setPrice(gamePayload.price());
        game.setCurrency((gamePayload.currency() != null) ? gamePayload.currency() : "USD");
        game.setBrand(gamePayload.brand());
        game.setDateAdded(LocalDate.now());
        game.setRating(1.00F);
        var gameSave = gameRepository.save(game);

        var firstImg = true; //TODO сделать обработку ошибок фотографий
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
                gameRepository.save(gameSave);
            }
        } catch (IOException e) {
        }

        return gameSave;
    }

    @Transactional
    public Game addGameOfTheDay(Long gameId, Integer discount, Locale locale) {
        var gameOfTheDay = gameRepository.findByGameOfTheDay(true);
        if (gameOfTheDay != null) {
            gameOfTheDay.setGameOfTheDay(false);
            gameOfTheDay.setDiscount(null);
            gameOfTheDay.setPriceWithDiscount(null);
            gameRepository.save(gameOfTheDay);
        }

        var game = findGame(gameId, locale);
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
    public List<Game> addGameSale(Integer discount, Locale locale, Long... gameIds) {
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
    public Game update(Long id, UpdateGamePayload gamePayload, Locale locale) {
        var gameOld = findGame(id, locale);
        if (gameOld != null) {
            BeanUtils.copyProperties(gamePayload, gameOld, "gameId",
                    "currency", "rating", "dateAdded", "categories");
            return gameRepository.save(gameOld);
        }
        return null;
    }

    @Transactional
    public void delete(Long id, Locale locale) {
        var game = findGame(id, locale);

        game.setCategories(new HashSet<>());
        gameRepository.save(game);

        gameRepository.delete(game);
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
    public void assignCategoriesToGame(Long idGame, Locale locale, Long... idCategories) {
        var game = findGame(idGame, locale);

        var categoriesFromGame = game.getCategories();
        var categories = new HashSet<Category>();
        for (var id : idCategories) {
            var category = findCategory(id, locale);
            categories.add(category);
        }

        categoriesFromGame.addAll(categories);

        var temp = new Game();
        BeanUtils.copyProperties(game, temp);
        temp.setCategories(categoriesFromGame);
        BeanUtils.copyProperties(temp, game);

        gameRepository.save(game);
    }

    @Transactional
    public void removeCategoriesFromGame(Long idGame, Locale locale, Long... idCategories) {
        var game = findGame(idGame, locale);

        var categoriesFromGame = game.getCategories();
        var categories = new HashSet<Category>();
        for (var id : idCategories) {
            var category = findCategory(id, locale);
            categories.add(category);
        }

        categoriesFromGame.removeAll(categories);

        var temp = new Game();
        BeanUtils.copyProperties(game, temp);
        temp.setCategories(categoriesFromGame);
        BeanUtils.copyProperties(temp, game);

        gameRepository.save(game);
    }

    public Integer getCount() {
        return gameRepository.findAll().size();
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
}
