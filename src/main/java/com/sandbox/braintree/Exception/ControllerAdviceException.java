package com.sandbox.braintree.Exception;

import com.sandbox.braintree.model.PaymentMethodResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ControllerAdviceException extends ResponseEntityExceptionHandler {
    private static final String CODE = "braintree";

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody PaymentMethodResponse handleResourceNotFound(final EmailNotFoundException exception) {

        PaymentMethodResponse error = new PaymentMethodResponse();
        error.setMessage(exception.getMessage());
        return error;
    }



}
