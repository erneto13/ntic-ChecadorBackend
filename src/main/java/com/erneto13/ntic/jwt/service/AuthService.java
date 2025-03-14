package com.erneto13.ntic.jwt.service;

import com.erneto13.ntic.jwt.controller.AuthRequest;
import com.erneto13.ntic.jwt.controller.RegisterRequest;
import com.erneto13.ntic.jwt.controller.TokenResponse;
import com.erneto13.ntic.jwt.repository.Token;
import com.erneto13.ntic.jwt.repository.TokenRepository;
import com.erneto13.ntic.model.Role;
import com.erneto13.ntic.model.User;
import com.erneto13.ntic.repository.RoleRepository;
import com.erneto13.ntic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(final RegisterRequest request) {
        Role role = roleRepository.findByName(request.role() != null ? request.role() : Role.ERole.STUDENT)
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));

        final User user = User.builder()
                .name(request.name())
                .email(request.email())
                .username(request.userName())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(role))
                .build();

        final User savedUser = repository.save(user);
        final String jwtToken = jwtService.generateToken(savedUser);
        final String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse authenticate(final AuthRequest request) {
        System.out.println("Intentando autenticar usuario: " + request.username());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );
            System.out.println("Autenticación exitosa para: " + request.username());

            final User user = repository.findByUsername(request.username())
                    .orElseThrow();
            final String accessToken = jwtService.generateToken(user);
            final String refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);
            return new TokenResponse(accessToken, refreshToken);
        } catch (Exception e) {
            System.out.println("Error al autenticar usuario: " + request.username());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        final Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final User user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setIsExpired(true);
                token.setIsRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse refreshToken(@NotNull final String authentication) {

        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }
        final String refreshToken = authentication.substring(7);
        final String userUsername = jwtService.extractUsername(refreshToken);
        if (userUsername == null) {
            return null;
        }

        final User user = this.repository.findByUsername(userUsername).orElseThrow();
        final boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);
        if (!isTokenValid) {
            return null;
        }

        final String accessToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken);
    }
}