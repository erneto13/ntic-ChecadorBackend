package com.erneto13.ntic.jwt.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("roles")
        List<String> roles
) {
}