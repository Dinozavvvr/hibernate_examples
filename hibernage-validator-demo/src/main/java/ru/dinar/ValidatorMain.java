package ru.dinar;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.dinar.model.Item;

import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class ValidatorMain {

    public static void main(String[] args) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Item item = new Item();
        item.setName("Some Item");
        item.setAuctionEnd(new Date());

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        ConstraintViolation<Item> violation = violations.iterator().next();
        String failedPropertyName =
                violation.getPropertyPath().iterator().next().getName();

        System.out.println(failedPropertyName);

        if (Locale.getDefault().getLanguage().equals("en")) {
            System.out.println(violation.getMessage());
        }
    }

}