package ru.yandex.practicum.filmorate.annotation.users.withoutSpaces;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WithoutSpacesValidator implements ConstraintValidator<WithoutSpaces, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
        try {
            return (!value.contains(" "));
        } catch (Exception e) {
            return true;
        }
    }
}
