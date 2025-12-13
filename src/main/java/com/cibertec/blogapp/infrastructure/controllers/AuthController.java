package com.cibertec.blogapp.infrastructure.controllers;

import com.cibertec.blogapp.application.usecases.dto.request.LoginRequest;
import com.cibertec.blogapp.application.usecases.dto.request.RegisterRequest;
import com.cibertec.blogapp.application.usecases.dto.response.AuthResponse;
import com.cibertec.blogapp.application.usecases.dto.response.UserResponse;
import com.cibertec.blogapp.domain.model.User;
import com.cibertec.blogapp.domain.services.interfaces.IUserService;
import com.cibertec.blogapp.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {

        User user = userService.register(
                request.getUsername(),
                request.getPassword()
        );

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getUsername());

        return new AuthResponse(token);
    }
}