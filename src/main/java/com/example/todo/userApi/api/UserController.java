package com.example.todo.userApi.api;

import com.example.todo.userApi.dto.request.LoginRequestDTO;
import com.example.todo.userApi.dto.request.UserSignUpRequestDTO;
import com.example.todo.userApi.dto.response.LoginResponseDTO;
import com.example.todo.userApi.dto.response.UserSignUpResponseDTO;
import com.example.todo.userApi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 회원가입 요청 처리
    // POST: /api/auth
    @PostMapping
    public ResponseEntity<?> signUp(
           @Validated @RequestBody UserSignUpRequestDTO dto,
            BindingResult result
    ) {
        log.info("/api/auth POST!! dto={}", dto);

        ResponseEntity<List<FieldError>> resultEntity = getFiledErrorResponseEntity(result);
        if (resultEntity != null) return resultEntity;

        try {
            UserSignUpResponseDTO responseDTO = userService.create(dto);
            log.info("save 완료 후 responseDTO 반환 중 in Controller dto={}", dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            log.info("save 완료 후 예외발생! in Controller");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인 요청 처리 메서드를 선언하세요.
    // LoginRequestDTO 클래스를 생성해서 요청 값을 받아 주세요.
    // service로 넘겨서 로그인 유효성을 검증하세요.(암호화되어 있어요)
    // 로그인 결과를 응답 상태 코드로 구분해서 보내 주세요.
    // 로그인이 성공했다면 200, 로그인 실패라면 400을 보내주세요. (에러 메세지를 상황에 따라 다르게 전달해 주세요)
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(
            @Validated @RequestBody LoginRequestDTO dto,
            BindingResult result
            ) {
        log.info("/api/auth/signin POST!!! LoginRequestDTO : {}", dto);

        ResponseEntity<List<FieldError>> resultEntity = getFiledErrorResponseEntity(result);
        if (resultEntity != null) return resultEntity;

        try {
            LoginResponseDTO responseDTO = userService.authenticate(dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    private static ResponseEntity<List<FieldError>> getFiledErrorResponseEntity(BindingResult result) {
        if (result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        return null;
    }
}
