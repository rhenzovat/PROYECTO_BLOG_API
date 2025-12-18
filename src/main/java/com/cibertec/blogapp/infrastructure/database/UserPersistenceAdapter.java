package com.cibertec.blogapp.infrastructure.database;

import com.cibertec.blogapp.domain.model.User;
import com.cibertec.blogapp.domain.services.UserPersistencePort;
import com.cibertec.blogapp.infrastructure.database.entities.UserEntity;
import com.cibertec.blogapp.infrastructure.database.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository repository;

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole());

        UserEntity saved = repository.save(entity);

        return new User(saved.getId(), saved.getUsername(), null, saved.getRole());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUsername(),
                        entity.getPassword(),
                        entity.getRole()
                ));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll()
                .stream()
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUsername(),
                        null,                // 🔐 nunca devolver password
                        entity.getRole()
                ))
                .toList();
    }

}

