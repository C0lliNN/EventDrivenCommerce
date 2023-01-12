package com.raphaelcollin.shippingmanagement.infrastructure.http;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionBody {
    String message;
    Iterable<ErrorDetail> details;

    public static ExceptionBody fromMessage(String message) {
        return new ExceptionBody(message, Collections.emptyList());
    }

    public static ExceptionBody fromException(Throwable exception) {
        return new ExceptionBody(exception.getMessage(), Collections.emptyList());
    }

    @Value
    public static class ErrorDetail {
        String field;
        String message;
    }
}