package dev.dubrovsky.marketbackend.payload.carousel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCarouselPayload(

        @NotBlank(message = "The carousel title must not be empty")
        @Size(min = 3, max = 50, message = "The carousel title must be between 3 and 50 characters")
        String title,

        @Size(max = 100, message = "The carousel description must be up to 100 characters")
        String text

) {
}
