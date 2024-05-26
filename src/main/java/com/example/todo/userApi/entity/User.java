package com.example.todo.userApi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter
@ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // 계정명이 아니라 식별 코드

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @CreationTimestamp
    private LocalDateTime joinDate;

    @Enumerated(EnumType.STRING) // 기본값은 ORDINAL으로, 숫자로 저장되기 때문에 STRING으로 지정
    @Builder.Default // 처음 USER 객체가 세팅될 때 아래와 같이 세팅하도록 설정
    private Role role = Role.COMMON; // 유저 권한
}
