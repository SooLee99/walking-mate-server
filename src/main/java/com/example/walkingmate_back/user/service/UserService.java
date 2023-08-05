package com.example.walkingmate_back.user.service;

import com.example.walkingmate_back.user.dto.UserResponse;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private UserResponse userResponse;

    private final EntityManager entityManager;

    public UserResponse passwordUpdate(String userId, String oldPw, String newPw) {
        log.info("userId:{}, oldPw:{}, newPw:{}", userId, oldPw, newPw);

        userResponse = new UserResponse();

        UserEntity user = entityManager.find(UserEntity.class, userId);
        if (user != null) {
            userResponse.data.userId = user.getId();

            if((user.getPw()).equals(oldPw)) {
                // TODO password update
                user.setPw(newPw);

                userResponse.data.code = userResponse.success;
                userResponse.data.message = "password update";
                return userResponse;
            }
            userResponse.data.message = "password wrong";
            userResponse.data.code = userResponse.fail;
            return userResponse;
        }
        userResponse.data.message = "user not found";
        userResponse.data.code = userResponse.fail;

        return userResponse;
    }


}
