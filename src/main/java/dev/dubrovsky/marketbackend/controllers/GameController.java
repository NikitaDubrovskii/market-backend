package dev.dubrovsky.marketbackend.controllers;

import dev.dubrovsky.marketbackend.dto.GameNewDTO;
import dev.dubrovsky.marketbackend.dto.GameOfTheDayDTO;
import dev.dubrovsky.marketbackend.dto.GamePopularDTO;
import dev.dubrovsky.marketbackend.dto.GameSaleDTO;
import dev.dubrovsky.marketbackend.models.Game;
import dev.dubrovsky.marketbackend.services.GameService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{id}")
    public Game getById(@PathVariable("id") Long id) {
        return gameService.getById(id);
    }

    @GetMapping("/popularGame")
    public List<GamePopularDTO> getPopularGame() {
        return gameService.getPopularGame();
    }

    @GetMapping("/gameOfTheDay")
    public GameOfTheDayDTO getGameOfTheDay() {
        return gameService.getGameOfTheDay();
    }

    @GetMapping("/newGame")
    public List<GameNewDTO> getNewGame() {
        return gameService.getNewGame();
    }

    @GetMapping("/saleGame")
    public List<GameSaleDTO> getSaleGame() {
        return gameService.getSaleGame();
    }

    @PostMapping("/add")
    public Game add (@ModelAttribute Game game,
                     @RequestParam("image") MultipartFile... files) {
        return gameService.add(game, files);
    }

    @PutMapping("/addGameOfTheDay/{id}")
    public Game addGameOfTheDay(@PathVariable("id") Long id, @RequestParam("discount") Integer discount) {
        return gameService.addGameOfTheDay(id, discount);
    }

    @PutMapping("/addGameSale")
    public List<Game> addGameSale(@RequestParam("discount") Integer discount, @RequestParam("id") Long... gameIds) {
        return gameService.addGameSale(discount, gameIds);
    }

    @PutMapping("/update/{id}")
    public Game update(@PathVariable("id") Long id, @RequestBody Game gameNew) {
        return gameService.update(id, gameNew);
    }

    @DeleteMapping("/delete/{id}")
    public Game delete(@PathVariable("id") Long id) {
        return gameService.delete(id);
    }

    @PutMapping("/removeSaleGame")
    public void removeSaleGame(@RequestParam("id") Long... gameIds) {
        gameService.removeGameSale(gameIds);
    }

    @PutMapping("/{idGame}/assign")
    public Game assignCategoriesToGame(@PathVariable("idGame") Long idGame, @RequestParam Long... idCategories) {
        return gameService.assignCategoriesToGame(idGame, idCategories);
    }

    @PutMapping("/{idGame}/remove")
    public Game removeCategoriesFromGame(@PathVariable("idGame") Long idGame, @RequestParam Long... idCategories) {
        return gameService.removeCategoriesFromGame(idGame, idCategories);
    }

}
