package com.example.todo.userApi.dto.response;

import com.example.todo.userApi.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


// 로그인 성공 후 클라이언트에게 전송할 데이터  객체
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
@Builder
public class LoginResponseDTO {

    private String email;
    private String userName;

    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDate joinDate;

    private String token; // 인증 토큰

    public LoginResponseDTO(User foundUser, String token) {
        this.email = foundUser.getEmail();
        this.userName = foundUser.getUserName();
        this.joinDate = LocalDate.from(foundUser.getJoinDate());
        this.token = token;
    }
}
