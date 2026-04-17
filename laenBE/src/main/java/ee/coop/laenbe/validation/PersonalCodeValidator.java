package ee.coop.laenbe.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class PersonalCodeValidator implements ConstraintValidator<ValidPersonalCode, String>{

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {

        if (code == null) return false;

        // 1. basic format check
        if (!code.matches("^[1-6]\\d{10}$")) {
            return false;
        }

        int firstDigit = Character.getNumericValue(code.charAt(0));

        int year = Integer.parseInt(code.substring(1, 3));
        int month = Integer.parseInt(code.substring(3, 5));
        int day = Integer.parseInt(code.substring(5, 7));

        // 2. determine full year
        int fullYear;
        switch (firstDigit) {
            case 1: case 2: fullYear = 1800 + year; break;
            case 3: case 4: fullYear = 1900 + year; break;
            case 5: case 6: fullYear = 2000 + year; break;
            default: return false;
        }

        // 3. validate real date
        try {
            LocalDate.of(fullYear, month, day);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}

