package com.cibertec.blogapp.application.usecases.dto.response;

import com.cibertec.blogapp.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private Role role;
}
