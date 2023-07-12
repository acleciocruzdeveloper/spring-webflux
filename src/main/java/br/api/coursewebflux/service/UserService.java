package br.api.coursewebflux.service;

import br.api.coursewebflux.entity.User;
import br.api.coursewebflux.mapper.UserMapper;
import br.api.coursewebflux.model.request.UserRequest;
import br.api.coursewebflux.repositories.UserRepository;
import br.api.coursewebflux.service.exception.ObjectNotFoundExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    public Mono<User> save(final UserRequest request) {
        return repository.save(mapper.toEntity(request));
    }

    public Mono<User> findById(String id) {
        return handleNotFound(repository.findById(id), id);
    }

    public Mono<User> updateUser(final String id, final UserRequest request) {
        return findById(id)
                .map(entity -> mapper.toEntity(request, entity)).flatMap(
                        repository::save);
    }

    public Flux<User> findAll() {
        return repository.findAll();
    }

    public Mono<User> deleteUser(final String id) {
        return handleNotFound(repository.findByIdAndRemove(id), id);
    }

    private <T> Mono<T> handleNotFound(Mono<T> mono, String id) {
        return mono.switchIfEmpty(
                Mono.error(new ObjectNotFoundExceptions(
                        format("Object Not Found id: %s, Type: %s", id, User.class.getSimpleName())
                )));
    }
}
