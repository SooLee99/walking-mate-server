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

    public UserResponse updateInfo(String userId, UserEntity user){
        userResponse = new UserResponse();
        Optional<UserEntity> newUser = userRepository.findById(userId);

        // transactional 적용하면 set 하고 save 안해줘도 변경된 값 DB에 반영됨 ㅋㅋ

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
