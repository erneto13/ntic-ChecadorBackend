package com.erneto13.ntic.jwt.controller;

import com.erneto13.ntic.model.Role.ERole;

public record RegisterRequest(
        String name,
        String username,
        String email,
        String password,
        ERole role
) {
}