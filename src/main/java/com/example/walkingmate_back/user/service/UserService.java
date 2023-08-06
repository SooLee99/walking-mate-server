package com.example.walkingmate_back.user.service;

import com.example.walkingmate_back.user.dto.UserResponse;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
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

    public UserEntity FindUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }

    public UserEntity FindUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }

    public UserResponse passwordUpdate(String userId, String oldPw, String newPw) {
        log.info("userId:{}, oldPw:{}, newPw:{}", userId, oldPw, newPw);

        userResponse = new UserResponse();

        Optional<UserEntity> user = userRepository.findById(userId);
        user.ifPresentOrElse(
                userEntity -> {
            userResponse.data.userId = userEntity.getId();

            if (userEntity.getPw().equals(oldPw)) {
                // TODO password update
                userEntity.setPw(newPw);
                userRepository.save(userEntity);

                userResponse.data.code = userResponse.success;
                userResponse.data.message = "password update";
            } else{
                userResponse.data.message = "password wrong";
                userResponse.data.code = userResponse.fail;
            }
        }, () -> {
               userResponse.data.message = "user not found";
               userResponse.data.code = userResponse.fail;
            }
        );
        return userResponse;
    }


}
