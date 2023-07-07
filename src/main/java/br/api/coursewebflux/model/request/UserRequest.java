package br.api.coursewebflux.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @Size(min = 3, max = 50)
        @NotBlank(message = "campo não pode ser vazio")
        String name,
        @Email(message = "e-mail invalido")
        @NotBlank(message = "campo nao pode ser vazio")
        String email,
        @Size(min = 6, max = 8)
        @NotBlank(message = "a senha precisa ter entre 6 e 8 caracteres")
        String password
) {
}
