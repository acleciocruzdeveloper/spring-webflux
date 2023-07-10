package br.api.coursewebflux.service;

import br.api.coursewebflux.entity.User;
import br.api.coursewebflux.mapper.UserMapper;
import br.api.coursewebflux.model.request.UserRequest;
import br.api.coursewebflux.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserService service;


    @Test
    @DisplayName("deve salvar um User com sucesso no repository")
    public void shouldSalveUserInRepositoryWithSuccess() {
        UserRequest request = new UserRequest("José Maria", "josemaria@email.com", "123456");
        User user = User.builder().build();

        Mockito.when(mapper.toEntity(any(UserRequest.class))).thenReturn(user);
        Mockito.when(repository.save(any(User.class))).thenReturn(Mono.just(user));

        Mono<User> result = service.save(request);

        StepVerifier.create(result)
                .expectNextMatches(Objects::nonNull)
                .expectComplete()
                .verify();
    }

}