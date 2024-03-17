package ru.yandex.practicum.filmorate.annotation.films.realeseDate;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AcceptableReleaseDateValidator implements ConstraintValidator<AcceptableReleaseDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return !value.isBefore(LocalDate.of(1895, 12, 28));
        } catch (Exception e) {
            return true;
        }
    }
}
