package dev.dubrovsky.marketbackend.facade;

import dev.dubrovsky.marketbackend.dto.GameNewDTO;
import dev.dubrovsky.marketbackend.dto.GameOfTheDayDTO;
import dev.dubrovsky.marketbackend.dto.GamePopularDTO;
import dev.dubrovsky.marketbackend.dto.GameSaleDTO;
import dev.dubrovsky.marketbackend.model.Game;
import dev.dubrovsky.marketbackend.model.Image;
import org.springframework.stereotype.Component;

@Component
public class GameFacade {

    public GamePopularDTO gameToGamePopularDTO(Game game) {
        var gamePreviewDTO = new GamePopularDTO();
        var preview = game.getImages().stream().filter(Image::isPreview).findFirst().get();

        gamePreviewDTO.setGameId(game.getGameId());
        gamePreviewDTO.setName(game.getName());
        gamePreviewDTO.setDescription(game.getDescription());
        gamePreviewDTO.setPrice(game.getPrice());
        gamePreviewDTO.setCurrency(game.getCurrency());
        gamePreviewDTO.setRating(game.getRating());
        gamePreviewDTO.setImagePreview(preview);

        return gamePreviewDTO;
    }

    public GameOfTheDayDTO gameToGameOfTheDay(Game game) {
        var gameOfTheDayDTO = new GameOfTheDayDTO();
        var preview = game.getImages().stream().filter(Image::isPreview).findFirst().get();

        gameOfTheDayDTO.setGameId(game.getGameId());
        gameOfTheDayDTO.setName(game.getName());
        gameOfTheDayDTO.setDescription(game.getDescription());
        gameOfTheDayDTO.setPrice(game.getPrice());
        gameOfTheDayDTO.setCurrency(game.getCurrency());
        gameOfTheDayDTO.setImagePreview(preview);
        gameOfTheDayDTO.setDiscount(game.getDiscount());
        gameOfTheDayDTO.setPriceWithDiscount(game.getPriceWithDiscount());

        return gameOfTheDayDTO;
    }

    public GameNewDTO gameToGameNewDTO(Game game) {
        var gameNewDTO = new GameNewDTO();
        var preview = game.getImages().stream().filter(Image::isPreview).findFirst().get();

        gameNewDTO.setGameId(game.getGameId());
        gameNewDTO.setName(game.getName());
        gameNewDTO.setDescription(game.getDescription());
        gameNewDTO.setPrice(game.getPrice());
        gameNewDTO.setCurrency(game.getCurrency());
        gameNewDTO.setDateAdded(game.getDateAdded());
        gameNewDTO.setImagePreview(preview);

        return gameNewDTO;
    }

    public GameSaleDTO gameToGameSaleDTO(Game game) {
        var gameSaleDTO = new GameSaleDTO();
        var preview = game.getImages().stream().filter(Image::isPreview).findFirst().get();

        gameSaleDTO.setGameId(game.getGameId());
        gameSaleDTO.setName(game.getName());
        gameSaleDTO.setDescription(game.getDescription());
        gameSaleDTO.setPrice(game.getPrice());
        gameSaleDTO.setCurrency(game.getCurrency());
        gameSaleDTO.setImagePreview(preview);
        gameSaleDTO.setDiscount(game.getDiscount());
        gameSaleDTO.setPriceWithDiscount(game.getPriceWithDiscount());

        return gameSaleDTO;
    }
}
