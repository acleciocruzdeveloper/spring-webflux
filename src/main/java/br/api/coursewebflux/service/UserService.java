package br.api.coursewebflux.service;

import br.api.coursewebflux.entity.User;
import br.api.coursewebflux.mapper.UserMapper;
import br.api.coursewebflux.model.request.UserRequest;
import br.api.coursewebflux.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    public Mono<User> save(final UserRequest request) {
        return repository.save(mapper.toEntity(request));
    }

    public Mono<User> findById(String id){
        return repository.findById(id);
    }


}
