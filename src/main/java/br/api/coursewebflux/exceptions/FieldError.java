package br.api.coursewebflux.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldError {
    private String message;
    private String fieldName;

}
