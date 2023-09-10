package com.example.walkingmate_back.login.service;

import com.example.walkingmate_back.login.domain.JoinRequest;
import com.example.walkingmate_back.login.domain.LoginRequest;
import com.example.walkingmate_back.login.domain.LoginResponse;
import com.example.walkingmate_back.login.utils.JwtUtil;
import com.example.walkingmate_back.user.entity.UserBody;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserBodyRepository;
import com.example.walkingmate_back.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class LoginService {

    @Value("${jwt.secret}")
    private String secretKey;
    private Long expiredMs = 1000 * 60 * 60l;

    private final UserRepository userRepository;
    private final UserBodyRepository userBodyRepository;

    private LoginResponse loginResponse;

    @Autowired
    public LoginService(UserRepository userRepository, UserBodyRepository userBodyRepository) {
        this.userRepository = userRepository;
        this.userBodyRepository = userBodyRepository;
    }

    public int join(JoinRequest joinRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 문자열 -> Date
        LocalDate date = LocalDate.parse(joinRequest.getBirth(), formatter);
        try{
            userRepository.save(UserEntity.builder()
                    .id(joinRequest.getId())
                    .pw(joinRequest.getPw())
                    .name(joinRequest.getName())
                    .phone(joinRequest.getPhone())
                    .birth(date)
                    .build());

            // 신체정보 저장
            UserBody userBody = new UserBody();
            userBody.setUserId(joinRequest.getId());
            userBody.setHeight(joinRequest.getHeight());
            userBody.setWeight(joinRequest.getWeight());
            userBodyRepository.save(userBody);
            return 1;
        } catch (Exception e){
            return 0;
        }

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

            if((user.get().getPw()).equals(password)) {
                loginResponse.data.jwt = JwtUtil.createJwt(userId, secretKey, expiredMs);
                loginResponse.data.code = loginResponse.success;
                loginResponse.data.message = "generate token";
                return loginResponse;
            }
            loginResponse.data.message = "잘못된 비밀번호";
            loginResponse.data.code = loginResponse.fail;
            return loginResponse;
        }
        loginResponse.data.message = "존재하지 않는 사용자";
        loginResponse.data.code = loginResponse.fail;

        return loginResponse;
    }
}