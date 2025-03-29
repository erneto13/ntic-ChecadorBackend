package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.Role;

public record UserResponse(
        Integer id, String name,
        String email,
        String username,
        Role.ERole role) {
}