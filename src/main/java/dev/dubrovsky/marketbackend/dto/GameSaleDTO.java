package dev.dubrovsky.marketbackend.dto;

import dev.dubrovsky.marketbackend.model.Image;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GameSaleDTO {

    private Long gameId;

    private String name;

    private String description;

    private BigDecimal price;

    private String currency;

    private Image imagePreview;

    private Integer discount;

    private BigDecimal priceWithDiscount;

}
