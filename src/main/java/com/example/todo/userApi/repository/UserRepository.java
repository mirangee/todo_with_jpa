package com.example.todo.userApi.repository;

import com.example.todo.userApi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
    
    // 이메일 중복 체크
//    @Query("SELECT COUNT(*) FROM User u WHERE u.email = :email") -> JPQL 사용 시
//    int findOneByEmail(@Param("email") String email);

    // 쿼리 메서드 existsBy<필드이름> 사용
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findOneByEmail(@Param("email") String email);
}
