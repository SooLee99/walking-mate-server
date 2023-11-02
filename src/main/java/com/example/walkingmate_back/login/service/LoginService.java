package com.example.walkingmate_back.login.service;

import com.example.walkingmate_back.login.domain.JoinRequest;
import com.example.walkingmate_back.login.domain.JoinResponseDTO;
import com.example.walkingmate_back.login.domain.LoginRequest;
import com.example.walkingmate_back.login.domain.LoginResponse;
import com.example.walkingmate_back.login.util.JwtUtil;
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

/**
 *    로그인, 회원가입
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.09.10
 *   @author           전우진, 이인범
 */

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
    private JoinResponseDTO joinResponseDTO;

    @Autowired
    public LoginService(UserRepository userRepository, UserBodyRepository userBodyRepository) {
        this.userRepository = userRepository;
        this.userBodyRepository = userBodyRepository;
    }

    /**
     * 회원가입
     * - 전우진, 이인범 2023.09.10
     */
    public JoinResponseDTO join(JoinRequest joinRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 문자열 -> LocalDate
        LocalDate date = LocalDate.parse(joinRequest.getBirth(), formatter);

        joinResponseDTO = new JoinResponseDTO();

        if(userRepository.existsById(joinRequest.getId()) == false) {
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

                joinResponseDTO.data.code = joinResponseDTO.success;
                joinResponseDTO.data.message = "회원가입 성공";
                return joinResponseDTO;
            } else {
                joinResponseDTO.data.code = joinResponseDTO.fail;
                joinResponseDTO.data.message = "중복된 아이디";
                return joinResponseDTO;
            }
    }

    /**
     * 로그인
     * - 이인범
     */
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