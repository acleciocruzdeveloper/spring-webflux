package br.api.coursewebflux.exceptions;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrors extends StandardError implements Serializable {
    @Serial
    private static final long serialVersionUID = 2315046595422956374L;
    private final List<FieldError> errors = new ArrayList<>();
    ValidationErrors(LocalDateTime timesTamp, String message, String path, Integer status, String error) {
        super(timesTamp, message, path, status, error);
    }
    public void addError(String message, String fieldName){
        this.errors.add(new FieldError(message, fieldName));
    }
}
