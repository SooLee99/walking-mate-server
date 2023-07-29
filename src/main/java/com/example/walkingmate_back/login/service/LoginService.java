package com.example.walkingmate_back.login.service;

import com.example.walkingmate_back.login.domain.JoinRequest;
import com.example.walkingmate_back.login.domain.LoginRequest;
import com.example.walkingmate_back.login.domain.LoginResponse;
import com.example.walkingmate_back.login.utils.JwtUtil;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class LoginService {
    @Value("${jwt.secret}")
    private String secretKey;
    private Long expiredMs = 1000 * 60 * 60l;

    private UserRepository userRepository;

    private LoginResponse loginResponse;

    public void join(JoinRequest joinRequest) {
        UserEntity user = UserEntity.builder()
                .id(joinRequest.getId())
                .pw(joinRequest.getPw())
                .name(joinRequest.getName())
                .phone(joinRequest.getPhone())
                .birth(joinRequest.getBirth())
                .build();

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();
        log.info("userName:{}, password:{}", userId, password);

        loginResponse = new LoginResponse();

        // 인증 과정
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            loginResponse.data.userId = user.get().getId();

            if(user.get().getPw() == password) {
                loginResponse.data.jwt = JwtUtil.createJwt(userId, secretKey, expiredMs);
                loginResponse.data.code = loginResponse.success;
                loginResponse.data.message = "generate token";
                return loginResponse;
            }
            loginResponse.data.message = "password wrong";
        }
        loginResponse.data.message = "user not found";
        loginResponse.data.code = loginResponse.fail;

        return loginResponse;
    }
}