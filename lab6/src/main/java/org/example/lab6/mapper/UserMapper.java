package org.example.lab6.mapper;

import org.example.lab6.model.dto.UserDto;
import org.example.lab6.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .telegramChatId(user.getTelegramChatId())
                .deviceToken(user.getDeviceToken())
                .createdAt(user.getCreatedAt())
                .build();
    }
}