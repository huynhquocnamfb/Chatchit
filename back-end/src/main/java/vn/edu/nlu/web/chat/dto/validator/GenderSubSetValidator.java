package vn.edu.nlu.web.chat.dto.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.edu.nlu.web.chat.dto.validator.annotations.GenderSubset;
import vn.edu.nlu.web.chat.enums.Gender;

import java.util.Arrays;

public class GenderSubSetValidator implements ConstraintValidator<GenderSubset, Gender> {
    private Gender[] genders;

    @Override
    public void initialize(GenderSubset constraint) {
        this.genders = constraint.anyOf();
    }

    @Override
    public boolean isValid(Gender value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(genders).contains(value);
    }
}