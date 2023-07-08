package br.api.coursewebflux.exceptions;

import br.api.coursewebflux.core.ExceptionsHandlerCore;
import br.api.coursewebflux.service.exception.ObjectNotFoundExceptions;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerExceptionHandler implements ExceptionsHandlerCore {

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<Mono<StandardError>> duplicateKeyException(
            DuplicateKeyException e, ServerHttpRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(Mono.just(StandardError.builder()
                        .timesTamp(now())
                        .path(request.getPath().toString())
                        .status(BAD_REQUEST.value())
                        .error(BAD_REQUEST.getReasonPhrase())
                        .message(verifyDupKey(e.getMessage()))

                        .build()));


    }

    @ExceptionHandler(WebExchangeBindException.class)
    ResponseEntity<Mono<ValidationErrors>> errorInputException(
            WebExchangeBindException exception, ServerHttpRequest request
    ) {
        ValidationErrors error = new ValidationErrors(
                now(), "error on valid input attributes",
                request.getPath().toString(),
                BAD_REQUEST.value(), "validation error"
        );

        for (FieldError e : exception.getBindingResult().getFieldErrors()
        ) {
            error.addError(e.getField(), e.getDefaultMessage());

        }
        return ResponseEntity.status(BAD_REQUEST).body(Mono.just(error));

    }

    @ExceptionHandler(ObjectNotFoundExceptions.class)
    ResponseEntity<Mono<StandardError>> objectNotFoundException(
            ObjectNotFoundExceptions e, ServerHttpRequest request
    ) {
        return ResponseEntity.status(NOT_FOUND)
                .body(Mono.just(StandardError.builder()
                        .timesTamp(now())
                        .path(request.getPath().toString())
                        .status(NOT_FOUND.value())
                        .error(NOT_FOUND.getReasonPhrase())
                        .message(e.getMessage())
                        .build()));


    }

    @Override
    public String verifyDupKey(String message) {
        if (message.contains("email dup key")) {
            return "E-mail already registered";
        }
        return "duplicate key error";
    }
}