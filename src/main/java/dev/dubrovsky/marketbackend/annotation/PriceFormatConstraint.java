package dev.dubrovsky.marketbackend.annotation;

import dev.dubrovsky.marketbackend.validation.PriceFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceFormatValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PriceFormatConstraint {

    String message() default "Price format must be in the form 0.00";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
