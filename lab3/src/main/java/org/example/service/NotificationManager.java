package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationManager {

    private final Map<String, MessageService> messageServices;

    public NotificationManager(Map<String, MessageService> messageServices) {
        this.messageServices = messageServices;
    }

    public void notify(String type, String message, String recipient) {
        MessageService service = messageServices.get(type);

        if (service == null) {
            System.out.println("Сервис не найден: " + type);
            return;
        }

        service.sendMessage(message, recipient);
    }
}