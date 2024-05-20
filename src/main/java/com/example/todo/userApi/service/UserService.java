package com.example.todo.userApi.service;

import com.example.todo.userApi.dto.request.LoginRequestDTO;
import com.example.todo.userApi.dto.response.UserSignInResponseDTO;
import com.example.todo.userApi.dto.response.UserSignUpResponseDTO;
import com.example.todo.userApi.dto.request.UserSignUpRequestDTO;
import com.example.todo.userApi.entity.User;
import com.example.todo.userApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserSignUpResponseDTO create(final UserSignUpRequestDTO dto) throws Exception {
        if(isDuplicate(dto.getEmail())){
           throw new RuntimeException("Email is already in use");
        };

        // 패스워드 인코딩
        String encoded = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encoded);

        User saved = userRepository.save(dto.toEntity());
        log.info("Saved: " + saved);

        return new UserSignUpResponseDTO(saved);
    }

    public UserSignInResponseDTO login(LoginRequestDTO dto) throws Exception {
        User foundUser = userRepository.findOneByEmail(dto.getEmail());
        if(foundUser == null){
            throw new RuntimeException("User not found");
        } else if(passwordEncoder.matches(dto.getPassword(), foundUser.getPassword())){
            return new UserSignInResponseDTO(foundUser);
        } else {
            throw new RuntimeException("Wrong password");
        }
    }
}
