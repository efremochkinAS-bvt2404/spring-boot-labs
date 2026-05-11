package org.example.lab6.service;

import org.example.lab6.model.dto.NotificationDto;
import org.example.lab6.model.entity.Notification;
import org.example.lab6.model.entity.User;
import org.example.lab6.model.enums.NotificationChannel;
import org.example.lab6.repository.NotificationRepository;
import org.example.lab6.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void shouldCreateNotification() {

        User user = new User();
        user.setId(1L);
        user.setEmail("ivan@example.com");

        NotificationDto dto = NotificationDto.builder()
                .title("Напоминание")
                .message("Завтра пара по Spring")
                .channel(NotificationChannel.EMAIL)
                .recipientId(1L)
                .build();

        Notification savedNotification = new Notification();
        savedNotification.setTitle(dto.getTitle());
        savedNotification.setMessage(dto.getMessage());
        savedNotification.setChannel(dto.getChannel());
        savedNotification.setRecipient(user);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(notificationRepository.save(any(Notification.class)))
                .thenReturn(savedNotification);

        Notification result =
                notificationService.createNotification(dto);

        assertNotNull(result);
        assertEquals("Напоминание", result.getTitle());
        assertEquals(NotificationChannel.EMAIL, result.getChannel());
        assertEquals(user, result.getRecipient());
    }

    @Test
    void shouldGetNotificationById() {
        User user = new User();
        user.setId(1L);

        Notification notification = new Notification();
        notification.setId(1L);
        notification.setTitle("Напоминание");
        notification.setMessage("Завтра пара");
        notification.setRecipient(user);

        when(notificationRepository.findById(1L))
                .thenReturn(Optional.of(notification));

        Notification result = notificationService.getNotificationById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Напоминание", result.getTitle());
        assertEquals("Завтра пара", result.getMessage());

        verify(notificationRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenNotificationNotFound() {
        when(notificationRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                notificationService.getNotificationById(99L)
        );

        verify(notificationRepository).findById(99L);
    }
}

