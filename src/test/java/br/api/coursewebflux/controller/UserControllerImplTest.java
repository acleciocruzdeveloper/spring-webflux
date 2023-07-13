package br.api.coursewebflux.controller;

import br.api.coursewebflux.entity.User;
import br.api.coursewebflux.mapper.UserMapper;
import br.api.coursewebflux.model.request.UserRequest;
import br.api.coursewebflux.model.response.UserResponse;
import br.api.coursewebflux.service.UserService;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {
    public static final String ID = "123abc";
    private static final String BASE_URI = "/users";
    public static final String NAME = "João Aparecido";
    public static final String EMAIL = "joao@email.com";
    public static final String PASSWORD = "123456";
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private UserService service;
    @MockBean
    private UserMapper mapper;
    @MockBean
    private MongoClient mongoClient;

    @Test
    @DisplayName("Should save user with success")
    void shouldSaveUserWithSuccess() {

        UserRequest userRequest = new UserRequest("Raul", "raul@gmail.com", "123456");

        Mockito.when(service.save(any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

        webTestClient.post().uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userRequest))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(service).save(any(UserRequest.class));

    }

    @Test
    @DisplayName("do not Should save user with bad request")
    void doNotShouldSaveUserWithBadRequest() {

        UserRequest userRequest = new UserRequest(" Raul", "raul@gmail.com", "123456");

        Mockito.when(service.save(any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

        webTestClient.post().uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userRequest))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo(BASE_URI)
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("validation error")
                .jsonPath("$.errors[0].message").isEqualTo("name")
                .jsonPath("$.errors[0].fieldName")
                .isEqualTo("field cannot have spaces at the beginning or end");

    }

    @Test
    @DisplayName("should return user with id valid")
    void shouldReturnUserSuccess() {

        UserResponse userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

        Mockito.when(service.findById(anyString())).thenReturn(Mono.just(User.builder().build()));
        Mockito.when(mapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.get().uri(BASE_URI + "/" + ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.name").isEqualTo(NAME)
                .jsonPath("$.email").isEqualTo(EMAIL)
                .jsonPath("$.password").isEqualTo(PASSWORD);

        Mockito.verify(service).findById(anyString());
    }

    @Test
    @DisplayName("should list of user with success")
    void shouldReturnFluxTheUserSuccess() {
        UserResponse userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

        Mockito.when(service.findAll()).thenReturn(Flux.just(User.builder().build()));
        Mockito.when(mapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.get().uri(BASE_URI)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(service).findAll();

    }

    @Test
    @DisplayName("should update user with success")
    void shouldUpdateUserWithSuccess() {
        UserRequest userRequest = new UserRequest(NAME, EMAIL, PASSWORD);
        UserResponse userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

        Mockito.when(service.updateUser(anyString(), any(UserRequest.class)))
                .thenReturn(Mono.just(User.builder().build()));

        Mockito.when(mapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.put().uri(BASE_URI + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.name").isEqualTo(NAME)
                .jsonPath("$.email").isEqualTo(EMAIL)
                .jsonPath("$.password").isEqualTo(PASSWORD);

        Mockito.verify(service).updateUser(ID, userRequest);

    }

    @Test
    @DisplayName("should delete user success with id valid")
    void shouldDeleteUserWithSuccess() {

        Mockito.when(service.deleteUser(anyString())).thenReturn(Mono.just(User.builder().build()));

        webTestClient.delete().uri(BASE_URI + "/" + ID)
                .exchange()
                .expectStatus().isNoContent();

        Mockito.verify(service).deleteUser(ID);

    }
}