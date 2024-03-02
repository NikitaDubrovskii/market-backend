package dev.dubrovsky.marketbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_preview")
    private boolean preview;

    @Column(name = "image_bytes")
    private byte[] imageBytes;

    @Column(name = "game_id")
    private Long gameId;

}
