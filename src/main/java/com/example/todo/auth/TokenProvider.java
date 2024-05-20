package com.example.todo.auth;

import com.example.todo.userApi.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
//역할: 토큰을 발급하고 서명 위조를 검사하는 객체
public class TokenProvider {

    // 시그니처에 사용할 값 (512비트 이상의 랜덤 문자열을 권장)
    // @Value: properties 형태의 파일 내용을 읽어서 변수에 대입해주는 아노테이션(yml도 가능)
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /*
     * JSON WEB TOKEN을 생성하는 메서드
     * @param userEntity - 토큰의 내용(클레임)에 포함될 유저 정보
     * @return - 생성된 JSON을 암호화한 토큰값
     */
    public String createToken(User userEntity) {
        // 토큰 만료 시간 생성
        Date expiry = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS)
        );

        // 토큰 생성
         /*
            {
                "iss": "서비스 이름(발급자)", (issuer)
                "exp": "2023-12-27(만료일자)", (expiry date)
                "iat": "2023-11-27(발급일자)", (issued at)
                "email": "로그인한 사람 이메일", 
                "role": "Premium"
                ...
                == 서명
            }
         */
        // 만약 추가 클레임이 있다면 Map으로 정의한 후 넣으면 된다.
        Map<String, String> claims = new HashMap<>();
        claims.put("email", userEntity.getEmail());
//        claims.put("role", userEntity.getRole());
        
        return Jwts.builder()
                // token header에 들어갈 서명
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),
                        SignatureAlgorithm.ES512
                )
                // token payload에 들어갈 클레임 설정
                .setIssuer("Todo운영자") // iss: 발급자 정보
                .setIssuedAt(new Date()) // iat: 발급 시간
                .setExpiration(expiry) // exp: 만료 시간
                .setSubject(userEntity.getId()) //sub: 토큰을 식별할 수 있는 주요 데이터
                .setClaims(claims) // 위에서 map으로 만들어 놓은 추가하고자 하는 정보
                .compact(); // 암호화 문자열로 반환하기 위해 compact() 사용
    }
}
