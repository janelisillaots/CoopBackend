package ee.coop.laenbe.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PersonalCodeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPersonalCode {

    String message() default "Invalid Estonian personal code (isikukood)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}