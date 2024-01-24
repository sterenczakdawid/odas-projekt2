package odas.sterencd.odasprojekt.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailValid, String> {
    @Override
    public void initialize(EmailValid arg0) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

//        Część przed @ (lokalna część):
//
//        1. Może zawierać litery (zarówno małe, jak i duże).
//        2. Może zawierać cyfry.
//        3. Może zawierać znaki specjalne: _!#$%&’*+/=?{|}~^.-`.
//        4. Musi mieć co najmniej jeden znak.
//        5. Dowolny inny znak nie wymieniony w powyższych punktach nie jest dozwolony.
//
//        Znak @:
//        1. Pojedynczy znak @, który jest wymagany w adresie e-mail.
//
//        Część po @ (domena):
//        1. Może zawierać litery (zarówno małe, jak i duże).
//        2. Może zawierać cyfry.
//        3. Może zawierać kropki (`.``).
//        4. Musi mieć co najmniej jeden znak.

        String regex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern p = Pattern.compile(regex);

        if (email == null) {
            return false;
        }

        Matcher m = p.matcher(email);

        if (m.matches()) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Email nie spełnia wymagań!")
                .addConstraintViolation();
        return false;
    }
}
