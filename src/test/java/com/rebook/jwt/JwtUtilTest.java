package com.rebook.jwt;

import com.rebook.user.service.dto.AuthClaims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JwtUtilTest {
    @Autowired
    JwtUtil jwtUtil;

    @Test
    void createToken() {
        String token = jwtUtil.createToken(new AuthClaims(1L), Instant.now());
        System.out.println("token = " + token);
    }
}
