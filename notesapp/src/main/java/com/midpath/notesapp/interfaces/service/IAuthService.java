package com.midpath.notesapp.interfaces.service;

import com.midpath.notesapp.dto.requests.LoginRequest;
import com.midpath.notesapp.dto.requests.RegisterRequest;
import com.midpath.notesapp.dto.responses.AuthResponse;

public interface IAuthService {
    public AuthResponse register(RegisterRequest request);
    public AuthResponse login(LoginRequest request);
}