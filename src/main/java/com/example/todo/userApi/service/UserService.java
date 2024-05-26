package com.example.todo.userApi.service;

import com.example.todo.auth.TokenProvider;
import com.example.todo.userApi.dto.request.LoginRequestDTO;
import com.example.todo.userApi.dto.response.LoginResponseDTO;
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
    private final TokenProvider tokenProvider;

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

    public LoginResponseDTO authenticate(LoginRequestDTO dto) throws Exception {

        // 이메일을 통해 회원 정보 조회
        User foundUser = userRepository.findOneByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Not User Found"));

        // 패스워드 검증
        String rawPassword = dto.getPassword(); // 사용자가 입력한 비번
        String encodedPassword = foundUser.getPassword(); // DB에 저장된 암호화된 비번
        if(!passwordEncoder.matches(rawPassword,encodedPassword)){
            throw new RuntimeException("Wrong password");
        }

        log.info("{}님 로그인 성공! " + foundUser.getUserName());

        // 로그인 성공 후에 클라이언트에게 뭘 리턴해 줄 것인가?
        // -> JWT(JSON Web Token)을 클라이언트에게 발급해 주어야 한다(로그인 유지를 위한 세션의 대체제).
        String token = tokenProvider.createToken(foundUser);

        return new LoginResponseDTO(foundUser, token);
    }
}
