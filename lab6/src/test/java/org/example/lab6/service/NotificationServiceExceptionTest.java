package org.example.lab6.service;

import org.example.lab6.model.dto.NotificationDto;
import org.example.lab6.model.enums.NotificationChannel;
import org.example.lab6.repository.NotificationRepository;
import org.example.lab6.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceExceptionTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        NotificationDto dto = NotificationDto.builder()
                .title("Напоминание")
                .message("Сообщение")
                .channel(NotificationChannel.EMAIL)
                .recipientId(99L)
                .build();

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                notificationService.createNotification(dto));
    }
}

