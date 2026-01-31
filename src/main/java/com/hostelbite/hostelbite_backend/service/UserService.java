package com.hostelbite.hostelbite_backend.service;

import com.hostelbite.hostelbite_backend.dto.request.CreateUserRequestDto;
import com.hostelbite.hostelbite_backend.dto.request.UpdateUserRequestDto;
import com.hostelbite.hostelbite_backend.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(CreateUserRequestDto request);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    void deleteUser(Long id);

    UserResponseDto updateUser(Long id, UpdateUserRequestDto request);
}
