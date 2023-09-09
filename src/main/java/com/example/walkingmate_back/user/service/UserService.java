package com.example.walkingmate_back.user.service;

import com.example.walkingmate_back.user.dto.User;
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

    public UserResponse updateInfo(String userId, UserEntity user){
        userResponse = new UserResponse();
        Optional<UserEntity> newUser = userRepository.findById(userId);

        if (newUser.isPresent()) {
            newUser.get().setName(user.getName());
            newUser.get().setPhone(user.getPhone());
            newUser.get().setBirth(user.getBirth());
            userResponse.data.code = userResponse.success;
            userResponse.data.userId = userId;
            userResponse.data.message = "user info updated.";

        } else {
            userResponse.data.code = userResponse.fail;
            userResponse.data.userId = userId;
            userResponse.data.message = "user not found.";

        }
        return userResponse;
    }

    public User getInfo(String userId){
        UserEntity user = userRepository.findById(userId).orElse(null);
        User u = new User(user.getId(), user.getPw(), user.getName(), user.getPhone(), user.getBirth().toString());
        return u;
    }

    public UserEntity FindUser(String userId){
        UserEntity user = userRepository.findById(userId).orElse(null);
        return user;
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
