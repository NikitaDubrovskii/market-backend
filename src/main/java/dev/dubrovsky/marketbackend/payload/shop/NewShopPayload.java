package dev.dubrovsky.marketbackend.payload.shop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewShopPayload(

        @NotBlank(message = "The shop name must not be empty")
        @Size(min = 2, max = 50, message = "The shop name must be between 2 and 50 characters")
        String name,

        @Size(max = 50, message = "The shop name must be bup to 50 characters")
        String address,

        @Size(max = 20, message = "The shop phone must be bup to 20 characters")
        @Pattern(regexp = "^[\\\\+]?[(]?[0-9]{3}[)]?[-\\s\\\\.]?[0-9]{3}[-\\s\\\\.]?[0-9]{4,6}$",
                message = "The shop phone must be in the form +919367788755 or 8989829304 or +16308520397 or 786-307-3615")
        String phone,

        @Size(max = 1000, message = "The shop info must be bup to 20 characters")
        String info

) {
}
