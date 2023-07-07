package br.api.coursewebflux.exceptions;

import br.api.coursewebflux.core.ExceptionsHandlerCore;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

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

    @Override
    public String verifyDupKey(String message) {
        if (message.contains("email dup key")) {
            return "E-mail already registered";
        }
        return "duplicate key error";
    }
}
