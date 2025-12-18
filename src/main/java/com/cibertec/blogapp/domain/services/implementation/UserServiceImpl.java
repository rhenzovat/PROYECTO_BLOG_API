package com.cibertec.blogapp.domain.services.implementation;

import com.cibertec.blogapp.domain.model.Role;
import com.cibertec.blogapp.domain.model.User;
import com.cibertec.blogapp.domain.services.UserPersistencePort;
import com.cibertec.blogapp.domain.services.interfaces.IUserService;
import com.cibertec.blogapp.infrastructure.database.entities.UserEntity;
import com.cibertec.blogapp.infrastructure.database.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserPersistencePort userPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(String username, String password) {
        User user = new User(
                null,
                username,
                passwordEncoder.encode(password),
                Role.USER
        );

        return userPort.save(user);
    }
    @Override
    public User login(String username, String password) {
        throw new UnsupportedOperationException("Login not implemented yet");
    }

//    @Override
//    public List<User> getAllUsers() {
//        throw new UnsupportedOperationException("Get all users not implemented yet");
//    }

    @Override
    public List<User> getAllUsers() {
        return userPort.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userPort.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
