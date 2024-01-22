package odas.sterencd.odasprojekt.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    @Override
    public void initialize(PasswordValid arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        log.info("Sprawdzam");
//        Musi zawierać co najmniej jedną cyfrę ((?=.*[0-9])).
//        Musi zawierać co najmniej jedną małą literę ((?=.*[a-z])).
//        Musi zawierać co najmniej jedną dużą literę ((?=.*[A-Z])).
//        Musi zawierać co najmniej jeden znak specjalny ((?=.*[@#$%!^&+=,.()*])).
//        Nie może zawierać białych znaków ((?=\\S+$)).
//        Musi mieć długość między 8 a 32 znaki (.{8,64}).
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^&+=,.()*])(?=\\S+$).{8,32}$";
        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);

        if (m.matches()) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Hasło nie spełnia standardów bezpieczeństwa")
                .addConstraintViolation();
        return false;
    }
}
