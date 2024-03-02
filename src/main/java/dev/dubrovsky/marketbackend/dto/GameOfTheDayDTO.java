package dev.dubrovsky.marketbackend.dto;

import dev.dubrovsky.marketbackend.models.Image;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class GameOfTheDayDTO {

    private Long gameId;

    private String name;

    private String description;

    private BigDecimal price;

    private String currency;

    private Image imagePreview;

    private Integer discount;

    private BigDecimal priceWithDiscount;

}
