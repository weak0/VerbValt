package com.example.verbvaultjava.auth;

import com.example.verbvaultjava.config.JwtService;
import com.example.verbvaultjava.model.Role;
import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.repository.RoleRepository;
import com.example.verbvaultjava.repository.UserRepository;
import com.example.verbvaultjava.token.Token;
import com.example.verbvaultjava.token.TokenRepository;
import com.example.verbvaultjava.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<Role> byRoleName = roleRepository.findByRoleName(request.getRole());
        Role role;
        if (byRoleName.isEmpty()) {
            role = new Role();
            role.setRoleName(request.getRole());
        } else {
            role = byRoleName.get();
        }
        User user = User.builder()
                .nickName(request.getFirstName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        roleRepository.save(role);
        User userFromDb = repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(userFromDb, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validTokenByUser = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validTokenByUser.isEmpty()) {
            return;
        }
        validTokenByUser.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validTokenByUser);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .tokenType(TokenType.BEARER)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}