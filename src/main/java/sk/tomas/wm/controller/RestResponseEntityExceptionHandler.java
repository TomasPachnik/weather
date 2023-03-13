package sk.tomas.wm.controller;

import lombok.extern.slf4j.Slf4j;
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
import java.util.UUID;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleConflict(BusinessException e, WebRequest request) {
        ErrorResponse errorResponse = generateErrorResponse(e.getMessage());
        log.warn("Error with id '" + errorResponse.getId() + "':", e);
        return handleExceptionInternal(e, generateErrorResponse(e.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleConflict(Exception e, WebRequest request) {
        ErrorResponse errorResponse = generateErrorResponse("Server error.");
        log.error("Server error with id '" + errorResponse.getId() + "':", e);
        return handleExceptionInternal(e, generateErrorResponse(e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private static ErrorResponse generateErrorResponse(String message) {
        return ErrorResponse.builder()
                .id(UUID.randomUUID())
                .created(OffsetDateTime.now())
                .message(message)
                .build();
    }

}
