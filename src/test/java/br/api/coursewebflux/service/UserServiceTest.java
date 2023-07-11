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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    @DisplayName("deve retornar um usuario ao passar o id")
    public void shouldFindByIdUser(){
        Mockito.when(repository.findById(anyString())).thenReturn(Mono.just(User.builder().build()));

        Mono<User> response = service.findById("123");
        StepVerifier.create(response)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        verify(repository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("deve buscar um lista com os usuarios cadastrados")
    public void shouldFindAllUserRepository(){

        Mockito.when(repository.findAll()).thenReturn(Flux.just(User.builder().build()));

        Flux<User> response = service.findAll();

        StepVerifier.create(response)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("deve atualizar os dados de um usuario ao passar o ID")
    public void shouldUpdateDataUser() {
        UserRequest request = new UserRequest("José Maria", "josemaria@email.com", "123456");
        User entity = User.builder().build();

        Mockito.when(mapper.toEntity(any(UserRequest.class), any(User.class))).thenReturn(entity);
        Mockito.when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        Mockito.when(repository.save(any(User.class))).thenReturn(Mono.just(entity));

        Mono<User> response = service.updateUser("123", request);

        StepVerifier.create(response)
                .expectNextMatches(u ->  u.getClass() == User.class)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("deve deletar um user do repository ao passar um id valido")
    public void shouldDeleteUserWithIdValid(){
        User entity = User.builder().build();

        Mockito.when(repository.findByIdAndRemove(anyString())).thenReturn(Mono.just(entity));

        Mono<User> response = service.deleteUser("123abc");

        StepVerifier.create(response)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        verify(repository, times(1)).findByIdAndRemove(anyString());
    }

}