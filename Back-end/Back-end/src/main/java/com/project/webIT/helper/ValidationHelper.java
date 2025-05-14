package com.project.webIT.helper;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationHelper {
//    public static String extractErrorMessages(BindingResult result) {
//        List<String> errorMessages = result.getFieldErrors()
//                .stream()
//                .map(FieldError::getDefaultMessage)
//                .collect(Collectors.toList());
//        return String.join("; ", errorMessages);
//    }
    public static String extractDetailedErrorMessages(BindingResult result) {
        return result.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
    }
}
