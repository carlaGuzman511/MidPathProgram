package com.midpath.notesapp.security;

import com.midpath.notesapp.dto.requests.LoginRequest;
import com.midpath.notesapp.dto.requests.RegisterRequest;
import com.midpath.notesapp.dto.responses.AuthResponse;
import com.midpath.notesapp.entity.enums.Role;
import com.midpath.notesapp.entity.User;
import com.midpath.notesapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    //@Bean
    //public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    public AuthResponse register(RegisterRequest req) {
        if (users.existsByUsername(req.username())) throw new IllegalArgumentException("Username taken");
        if (users.existsByEmail(req.email())) throw new IllegalArgumentException("Email taken");

        User user = User.builder()
                .username(req.username())
                .email(req.email())
                .password(encoder.encode(req.password()))
                .roles(Set.of(Role.USER))
                .enabled(true)
                .build();
        
        users.save(user);

        String token = jwt.generateToken(user);

        return new AuthResponse(token, "Bearer", user.getUsername(), user.getEmail());
    }

    public AuthResponse login(LoginRequest req) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.usernameOrEmail(), req.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = users.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        String token = jwt.generateToken(user);
        return new AuthResponse(token, "Bearer", user.getUsername(), user.getEmail());
    }
}

