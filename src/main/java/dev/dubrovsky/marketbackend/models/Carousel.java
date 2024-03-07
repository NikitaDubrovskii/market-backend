package dev.dubrovsky.marketbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "carousel")
@Getter
@Setter
public class Carousel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carousel_id")
    private Long carouselId;

    @Column(name = "image_bytes")
    private byte[] imageBytes;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

}
