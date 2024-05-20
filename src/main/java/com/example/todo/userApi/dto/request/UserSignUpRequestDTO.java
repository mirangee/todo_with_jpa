package com.example.todo.userApi.dto.request;

import com.example.todo.userApi.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter @Getter
@ToString
@EqualsAndHashCode(of = "email") // 이메일이 같으면 같은 객체로 취급, 이것을 안 넣으면 모든 필드를 다 비교한다.
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserSignUpRequestDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    @Size(min = 2, max = 5)
    private String userName;

    public User toEntity() {
        User user = new User();
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        user.setUserName(this.getUserName());
        return user;
    }
}
