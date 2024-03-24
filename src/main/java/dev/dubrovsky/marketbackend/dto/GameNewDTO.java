package dev.dubrovsky.marketbackend.dto;

import dev.dubrovsky.marketbackend.model.Image;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class GameNewDTO {

    private Long gameId;

    private String name;

    private String description;

    private BigDecimal price;

    private String currency;

    private Image imagePreview;

    private LocalDate dateAdded;

}
