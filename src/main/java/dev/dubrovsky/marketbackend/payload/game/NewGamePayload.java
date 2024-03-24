package dev.dubrovsky.marketbackend.payload.game;

import dev.dubrovsky.marketbackend.annotation.PriceFormatConstraint;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record NewGamePayload(

        @NotBlank(message = "The game name must not be empty")
        @Size(min = 3, max = 100, message = "The game name must be between 3 and 100 characters")
        String name,

        @Size(max = 1000, message = "The game description must be up to 1000 characters")
        String description,

        @NotNull(message = "The game price must not be empty")
        @Positive(message = "The game price must be positive")
        @PriceFormatConstraint
        BigDecimal price,

        @Size(min = 2, max = 5, message = "The game currency must be between 2 and 5 characters")
        String currency,

        @Size(max = 50, message = "The game brand must be up to 50 characters")
        String brand

) {
}
