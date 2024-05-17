package com.example.todo.userApi.repository;

import com.example.todo.userApi.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("bulk insert")
    void bulkInsert() {
        for(int i=1; i<=30; i++) {
            userRepository.save(
                    User.builder()
                            .email("test" + i + "@example.com")
                            .userName("김테스트" + i)
                            .password("123456"+ i)
                            .build()
            );
        }
    }
}