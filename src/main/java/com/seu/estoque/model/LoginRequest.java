package com.seu.estoque.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String username; // Email
    private String password;
}