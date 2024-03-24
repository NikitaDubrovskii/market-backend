package dev.dubrovsky.marketbackend.controller;

import dev.dubrovsky.marketbackend.dto.GameNewDTO;
import dev.dubrovsky.marketbackend.dto.GameOfTheDayDTO;
import dev.dubrovsky.marketbackend.dto.GamePopularDTO;
import dev.dubrovsky.marketbackend.dto.GameSaleDTO;
import dev.dubrovsky.marketbackend.model.Game;
import dev.dubrovsky.marketbackend.payload.game.NewGamePayload;
import dev.dubrovsky.marketbackend.payload.game.UpdateGamePayload;
import dev.dubrovsky.marketbackend.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("game")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/{id}")
    public ResponseEntity<Game> getById(@PathVariable("id") Long id,
                                        Locale locale) {
        var game = gameService.getById(id, locale);
        return ResponseEntity.ok().body(game);
    }

    @GetMapping("/popularGame")
    public ResponseEntity<List<GamePopularDTO>> getPopularGame() {
        var popularGame = gameService.getPopularGame();
        return ResponseEntity.ok().body(popularGame);
    }

    @GetMapping("/gameOfTheDay")
    public ResponseEntity<GameOfTheDayDTO> getGameOfTheDay() {
        var gameOfTheDay = gameService.getGameOfTheDay();
        return ResponseEntity.ok().body(gameOfTheDay);
    }

    @GetMapping("/newGame")
    public ResponseEntity<List<GameNewDTO>> getNewGame() {
        var newGame = gameService.getNewGame();
        return ResponseEntity.ok().body(newGame);
    }

    @GetMapping("/saleGame")
    public ResponseEntity<List<GameSaleDTO>> getSaleGame() {
        var saleGame = gameService.getSaleGame();
        return ResponseEntity.ok().body(saleGame);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @ModelAttribute NewGamePayload gamePayload,
                                 BindingResult bindingResult,
                                 Locale locale,
                                 @RequestParam("image") MultipartFile... files) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        } else {
            var game = gameService.add(gamePayload, locale, files);
            return ResponseEntity.ok().body(game);
        }
    }

    @PutMapping("/addGameOfTheDay/{id}")
    public ResponseEntity<Game> addGameOfTheDay(@PathVariable("id") Long id,
                                @RequestParam("discount") Integer discount,
                                Locale locale) {
        var game = gameService.addGameOfTheDay(id, discount, locale);
        return ResponseEntity.ok().body(game);
    }

    @PutMapping("/addGameSale")
    public List<Game> addGameSale(@RequestParam("discount") Integer discount,
                                  Locale locale,
                                  @RequestParam("id") Long... gameIds) {
        return gameService.addGameSale(discount, locale, gameIds);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                       @Valid @ModelAttribute UpdateGamePayload gamePayload,
                       BindingResult bindingResult,
                       Locale locale) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        } else {
            var game = gameService.update(id, gamePayload, locale);
            return ResponseEntity.ok().body(game);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id,
                       Locale locale) {
        gameService.delete(id, locale);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/removeSaleGame")
    public ResponseEntity<Void> removeSaleGame(@RequestParam("id") Long... gameIds) {
        gameService.removeGameSale(gameIds);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idGame}/assign")
    public ResponseEntity<Void> assignCategoriesToGame(@PathVariable("idGame") Long idGame,
                                       Locale locale,
                                       @RequestParam Long... idCategories) {
        gameService.assignCategoriesToGame(idGame, locale, idCategories);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idGame}/remove")
    public ResponseEntity<Void> removeCategoriesFromGame(@PathVariable("idGame") Long idGame,
                                         Locale locale,
                                         @RequestParam Long... idCategories) {
        gameService.removeCategoriesFromGame(idGame, locale, idCategories);
        return ResponseEntity.noContent().build();
    }

}
