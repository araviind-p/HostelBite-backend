package com.hostelbite.hostelbite_backend.service.impl;

import com.hostelbite.hostelbite_backend.dto.request.CreateUserRequestDto;
import com.hostelbite.hostelbite_backend.dto.request.UpdateUserRequestDto;
import com.hostelbite.hostelbite_backend.dto.response.UserResponseDto;
import com.hostelbite.hostelbite_backend.entity.User;
import com.hostelbite.hostelbite_backend.exception.EmailAlreadyExistsException;
import com.hostelbite.hostelbite_backend.exception.ResourceNotFoundException;
import com.hostelbite.hostelbite_backend.repository.UserRepository;
import com.hostelbite.hostelbite_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto createUser(CreateUserRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword()) // hash later
                .build();

        User savedUser = userRepository.save(user);

        return UserResponseDto.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        return mapToDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

    private UserResponseDto mapToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        User updatedUser = userRepository.save(user);

        return mapToDto(updatedUser);
    }
}
