package dev.dubrovsky.marketbackend.controllers;

import dev.dubrovsky.marketbackend.models.Game;
import dev.dubrovsky.marketbackend.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping()
    public List<Game> getAll() {
        return gameService.getAll();
    }

    @GetMapping("/{id}")
    public Game getById(@PathVariable("id") Long id) {
        return gameService.getById(id);
    }

    @PostMapping("/add")
    public Game add (@RequestBody Game game) {
        return gameService.add(game);
    }

    @PutMapping("/update/{id}")
    public Game update(@PathVariable("id") Long id, @RequestBody Game gameNew) {
        return gameService.update(id, gameNew);
    }

    @DeleteMapping("/delete/{id}")
    public Game delete(@PathVariable("id") Long id) {
        return gameService.delete(id);
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
