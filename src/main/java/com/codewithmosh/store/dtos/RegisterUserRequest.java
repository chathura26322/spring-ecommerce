package com.codewithmosh.store.dtos;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String name;
    private String password;
    private String email;
}
