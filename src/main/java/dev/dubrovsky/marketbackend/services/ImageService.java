package dev.dubrovsky.marketbackend.services;

import dev.dubrovsky.marketbackend.models.Game;
import dev.dubrovsky.marketbackend.models.Image;
import dev.dubrovsky.marketbackend.repositories.GameRepository;
import dev.dubrovsky.marketbackend.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;
    private final GameRepository gameRepository;

    public ImageService(ImageRepository imageRepository, GameRepository gameRepository) {
        this.imageRepository = imageRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional
    public void addToGame(Long gameId, MultipartFile... file) throws IOException {
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game == null) return;

        Set<Image> images = new HashSet<>();

        for (MultipartFile img : file) {
            Image image = new Image();
            image.setGameId(game.getGameId());
            image.setName(img.getOriginalFilename());
            image.setImageBytes(img.getBytes());
            images.add(image);

            imageRepository.save(image);
        }

        //game.setImages(images);

        gameRepository.save(game);
    }

    public List<Image> getAllFromGame(Long gameId) {
        List<Image> images = imageRepository.findAllByGameId(gameId);
        return images;
    }
}
