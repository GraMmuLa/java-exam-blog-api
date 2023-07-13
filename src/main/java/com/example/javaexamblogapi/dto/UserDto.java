package com.example.javaexamblogapi.dto;

import com.example.javaexamblogapi.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDto implements ObjectDto<User> {
    private Long id;
    private String username;
    private String email;
    private String password;

    @Override
    public User toEntity() {
        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

    public static UserDto fromEntity(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

    public static UserDto fromEntity(AuthenticationRequestDto authenticationDto) {
        UserDto userDto = new UserDto();

        userDto.setUsername(authenticationDto.getUsername());
        userDto.setEmail(authenticationDto.getEmail());
        userDto.setPassword(authenticationDto.getPassword());

        return userDto;
    }

}
