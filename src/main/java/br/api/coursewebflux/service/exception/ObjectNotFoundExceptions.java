package br.api.coursewebflux.service.exception;

import java.io.Serial;

public class ObjectNotFoundExceptions extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1387549690273279701L;

    public ObjectNotFoundExceptions(String message) {
        super(message);
    }
}
