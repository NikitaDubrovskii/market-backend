package dev.dubrovsky.marketbackend.payload.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewNewsPayload(

        @NotBlank(message = "The news name must not be empty")
        @Size(min = 3, max = 100, message = "The news name must be between 3 and 100 characters")
        String title,

        @NotBlank(message = "The news description must not be empty")
        @Size(max = 100, message = "The news description must be up to 100 characters")
        String description,

        @Size(max = 1000, message = "The news text must be up to 1000 characters")
        String text

) {
}
