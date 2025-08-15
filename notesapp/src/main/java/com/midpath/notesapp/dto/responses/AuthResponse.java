package com.midpath.notesapp.dto.responses;

public record AuthResponse(
        String accessToken,
        String tokenType,
        String username,
        String email
) {}


