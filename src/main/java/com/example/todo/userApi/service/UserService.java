package com.example.todo.userApi.service;

import com.example.todo.userApi.entity.User;
import com.example.todo.userApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public boolean isDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }
}
