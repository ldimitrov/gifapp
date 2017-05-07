package com.lyubendimitrov.gifapp.validator;

import com.lyubendimitrov.gifapp.model.Gif;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class GifValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Gif.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Gif gif = (Gif) target;

        // validate if id is null (new gif), otherwise update works without new reupload.
        if (gif.getId() == null && (gif.getFile() == null || gif.getFile().isEmpty())) {
            errors.rejectValue("file", "file.required", "Please choose a file to upload.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.empty", "Please enter a description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "category.empty", "Please choose a category");
    }
}
