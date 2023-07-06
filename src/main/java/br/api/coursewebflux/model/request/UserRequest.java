package br.api.coursewebflux.model.request;

public record UserRequest(
        String name,
        String email,
        String password
) {
}
