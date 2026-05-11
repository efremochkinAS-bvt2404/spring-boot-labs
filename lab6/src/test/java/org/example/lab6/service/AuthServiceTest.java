package org.example.lab6.service;

import org.example.lab6.model.dto.RegisterRequest;
import org.example.lab6.model.entity.User;
import org.example.lab6.model.enums.UserRole;
import org.example.lab6.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUser() {
        RegisterRequest request = new RegisterRequest();

        request.setName("Иван");
        request.setEmail("ivan@example.com");
        request.setPassword("qwerty123");

        when(passwordEncoder.encode("qwerty123"))
                .thenReturn("encoded-password");

        authService.register(request);

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals("Иван", savedUser.getName());
        assertEquals("ivan@example.com", savedUser.getEmail());
        assertEquals("encoded-password", savedUser.getPassword());
        assertEquals(UserRole.ROLE_USER, savedUser.getRole());

        assertNotNull(savedUser.getCreatedAt());
    }
}