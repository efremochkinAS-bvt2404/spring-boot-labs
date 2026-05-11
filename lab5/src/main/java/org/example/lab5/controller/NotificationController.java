package org.example.lab5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lab5.model.dto.NotificationDto;
import org.example.lab5.model.entity.Notification;
import org.example.lab5.model.enums.NotificationChannel;
import org.example.lab5.model.enums.NotificationStatus;
import org.example.lab5.service.NotificationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/add")
    public NotificationDto createNotification(@RequestBody @Valid NotificationDto request) {
        Notification response = notificationService.createNotification(request);

        return mapToDto(response);
    }

    @GetMapping("/all")
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications().stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public NotificationDto getNotificationById(@PathVariable Long id) {
        Notification response = notificationService.getNotificationById(id);
        return mapToDto(response);
    }

    @PutMapping("/{id}")
    public NotificationDto updateNotification(@PathVariable Long id,
                                              @RequestBody @Valid
                                              NotificationDto request) {
        Notification response = notificationService.updateNotification(id, request);

        return mapToDto(response);
    }

    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "Уведомление удалено";
    }

    @GetMapping("/status/{status}")
    public List<NotificationDto> getByStatus(@PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByStatus(status).stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/channel/{channel}")
    public List<NotificationDto> getByChannel(@PathVariable NotificationChannel channel) {
        return notificationService.getNotificationsByChannel(channel).stream()
                        .map(this::mapToDto)
                        .toList();
    }

    @GetMapping("/recipient/{recipientId}")
    public List<NotificationDto> getByRecipientId(@PathVariable Long recipientId) {
        return notificationService.getNotificationsByRecipientId(recipientId).stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/sorted")
    public List<NotificationDto> getSorted() {
        return notificationService.getAllSorted().stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/status/{status}/channel/{channel}")
    public List<NotificationDto> getByStatusAndChannel(
            @PathVariable NotificationStatus status,
            @PathVariable NotificationChannel channel
    ) {
        return notificationService.getByStatusAndChannel(status, channel).stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/recipient/{recipientId}/status/{status}")
    public List<NotificationDto> getByRecipientIdAndStatus(
            @PathVariable Long recipientId,
            @PathVariable NotificationStatus status
    ) {
        return notificationService.getByRecipientIdAndStatus(recipientId, status).stream()
                .map(this::mapToDto)
                .toList();
    }

    private NotificationDto mapToDto(Notification notification) {
        return NotificationDto.builder()
                .title(notification.getTitle())
                .message(notification.getMessage())
                .channel(notification.getChannel())
                .status(notification.getStatus())
                .createdAt(notification.getCreatedAt())
                .sentAt(notification.getSentAt())
                .recipientId(notification.getRecipient().getId())
                .build();
    }
}