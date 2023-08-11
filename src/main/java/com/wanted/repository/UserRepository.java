package com.wanted.repository;

import com.wanted.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 중복 아이디 검사
    Boolean existsByEmail(String email);

    User findByEmail(String email);

}
