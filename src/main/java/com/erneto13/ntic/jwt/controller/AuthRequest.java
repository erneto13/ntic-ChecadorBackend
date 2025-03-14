package com.erneto13.ntic.jwt.controller;

public record AuthRequest(
        String username,
        String password
) {
}

