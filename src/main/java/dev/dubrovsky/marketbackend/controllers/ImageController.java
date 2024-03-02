package dev.dubrovsky.marketbackend.controllers;

import dev.dubrovsky.marketbackend.models.Image;
import dev.dubrovsky.marketbackend.services.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("image")
@CrossOrigin(origins = "http://localhost:4200")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/add/{gameId}")
    public void addImage(@PathVariable(name = "gameId") Long gameId,
                         @RequestParam("image") MultipartFile... files) throws IOException {
        imageService.addToGame(gameId, files);
    }

    @GetMapping("/{gameId}")
    public List<Image> get(@PathVariable(name = "gameId") Long gameId) {
        return imageService.getAllFromGame(gameId);
    }
}
