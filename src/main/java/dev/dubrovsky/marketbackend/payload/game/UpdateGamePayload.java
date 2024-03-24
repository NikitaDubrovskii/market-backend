package dev.dubrovsky.marketbackend.payload.game;

import dev.dubrovsky.marketbackend.annotation.PriceFormatConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateGamePayload(

        @NotBlank(message = "The game name must not be empty")
        @Size(min = 3, max = 100, message = "The game name must be between 3 and 100 characters")
        String name,

        @Size(max = 1000, message = "The game description must be up to 1000 characters")
        String description,

        @NotNull(message = "The game price must not be empty")
        @Positive(message = "The game price must be positive")
        @PriceFormatConstraint
        BigDecimal price,

        @Size(max = 50, message = "The game brand must be up to 50 characters")
        String brand

) {
}
