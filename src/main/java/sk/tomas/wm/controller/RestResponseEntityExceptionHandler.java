package sk.tomas.wm.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sk.tomas.wm.dto.ErrorResponse;
import sk.tomas.wm.exception.BusinessException;

import java.time.OffsetDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleConflict(BusinessException e, WebRequest request) {
        return handleExceptionInternal(e, generateErrorResponse(e.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private static ErrorResponse generateErrorResponse(String message) {
        return ErrorResponse.builder()
                .created(OffsetDateTime.now())
                .message(message)
                .build();
    }

}
