package com.cibertec.blogapp.domain.services;

import com.cibertec.blogapp.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {
    User save(User user);
    Optional<User> findByUsername(String username);
    List<User> findAll();


}
