package com.example.todo.userApi.api;

import com.example.todo.userApi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    // 이메일 중복 확인 요청 방식
    @GetMapping("/check")
    // GET: /api/auth/check?email=zzzz@mail.com
    // Clue: JPA는 pk로 조회하는 메서드는 기본 제공되지만 다른 컬럼으로 조회하는 메서드는 제공되지 않습니다.
    public ResponseEntity<?> emailCheck(@RequestParam("email") String email) {
        log.info("/api/auth/check?email={} GET!!!", email);
        if (email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("이메일이 없습니다.");
        }
        return ResponseEntity.ok().body(userService.isDuplicate(email));
    }
}
