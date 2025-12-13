package com.cibertec.blogapp.domain.services.interfaces;

import com.cibertec.blogapp.domain.model.User;

import java.util.List;

public interface IUserService {
    User register(String username, String password);

    User login(String username, String password);

    List<User> getAllUsers(); // solo admin
}

