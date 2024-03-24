package dev.dubrovsky.marketbackend.payload.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryPayload(

        @NotBlank(message = "The category name must not be empty")
        @Size(min = 3, max = 100, message = "The category name must be between 3 and 100 characters")
        String name

) {
}
