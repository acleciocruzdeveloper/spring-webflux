package br.api.coursewebflux.model.request;

import br.api.coursewebflux.validators.TrimString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @TrimString
        @Size(min = 3, max = 50, message = "o campo nome deve ter no minimo 3 caracteres")
        @NotBlank(message = "insira um valor valido")
        String name,
        @TrimString
        @Email(message = "e-mail invalido")
        @NotBlank(message = "campo nao pode ser vazio")
        String email,
        @TrimString
        @Size(min = 6, max = 8, message = "a senha deve conter entre 6 e 8 caracteres")
        @NotBlank(message = "senha invalida")
        String password
) {
}
