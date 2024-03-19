package ru.yandex.practicum.filmorate.annotation.users.withoutSpaces;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {WithoutSpacesValidator.class})
public @interface WithoutSpaces {

    String message() default "Логин не может содержать пробелов.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
