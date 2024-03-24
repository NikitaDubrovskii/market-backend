package dev.dubrovsky.marketbackend.validation;

import dev.dubrovsky.marketbackend.annotation.PriceFormatConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class PriceFormatValidator implements ConstraintValidator<PriceFormatConstraint, BigDecimal> {

    private static final Pattern PRICE_PATTERN = Pattern.compile("\\d+(\\.\\d{2})");

    @Override
    public void initialize(PriceFormatConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        String stringValue = value.toString();
        return stringValue.matches(String.valueOf(PRICE_PATTERN));
    }
}