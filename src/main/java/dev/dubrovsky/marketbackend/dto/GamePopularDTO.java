package dev.dubrovsky.marketbackend.dto;

import dev.dubrovsky.marketbackend.models.Image;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class GamePopularDTO {

    private Long gameId;

    private String name;

    private String description;

    private BigDecimal price;

    private String currency;

    private Float rating;

    private Image imagePreview;

}
