package org.example.lab6.controller;

import org.example.lab6.model.dto.LoginRequest;
import org.example.lab6.model.dto.RegisterRequest;
import org.example.lab6.security.JwtService;
import org.example.lab6.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return "Пользователь успешно зарегистрирован";
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            return jwtService.generateToken(request.getEmail());

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Неверный email или пароль"
            );
        }
    }

    @PostMapping("/register-admin")
    public String registerAdmin(@RequestBody @Valid RegisterRequest request) {
        authService.registerAdmin(request);
        return "Администратор успешно зарегистрирован";
    }
}