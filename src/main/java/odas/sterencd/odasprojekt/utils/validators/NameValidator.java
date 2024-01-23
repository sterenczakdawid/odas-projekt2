package odas.sterencd.odasprojekt.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<NameValid, String> {

    @Override
    public void initialize(NameValid arg0) {
    }
//    ^: Oznacza początek ciągu znaków.
//    [a-zA-Z_0-9!@#$%]: Oznacza, że każdy znak w ciągu musi być jednym z następujących: mała litera od a do z, duża litera od A do Z, cyfra od 0 do 9, lub jeden z symboli: !@#$%.
//    {6,255}: Oznacza, że ilość poprzedzających znaków (określonych przez poprzednią część wyrażenia) musi wynosić co najmniej 6, ale nie więcej niż 255.
//    $: Oznacza koniec ciągu znaków.

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        String regex = "^[a-zA-Z_0-9]{4,32}$";

        Pattern p = Pattern.compile(regex);

        if (name == null) {
            return false;
        }

        Matcher m = p.matcher(name);

        if (m.matches()) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Nazwa powinna składać się jedynie z liter i cyfr")
                .addConstraintViolation();
        return false;
    }
}
