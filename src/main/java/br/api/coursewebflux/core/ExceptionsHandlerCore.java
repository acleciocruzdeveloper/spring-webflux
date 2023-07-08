package br.api.coursewebflux.core;

import org.springframework.stereotype.Component;

@Component
public interface ExceptionsHandlerCore {

    public String verifyDupKey(String message);
}
