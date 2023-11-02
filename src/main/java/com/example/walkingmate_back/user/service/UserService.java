package com.example.walkingmate_back.user.service;

import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.repository.TeamMemberRepository;
import com.example.walkingmate_back.user.dto.User;
import com.example.walkingmate_back.user.dto.UserPwConfirmDTO;
import com.example.walkingmate_back.user.dto.UserResponse;
import com.example.walkingmate_back.user.dto.UserUpdateDTO;
import com.example.walkingmate_back.user.entity.UserBody;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserBodyRepository;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 *    사용자 정보 수정, 조회, 탈퇴, 비밀번호 재설정
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.09.11
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserBodyRepository userBodyRepository;
    private final TeamMemberRepository teamMemberRepository;
    private UserResponse userResponse;

    /**
     * 사용자 정보 수정
     * - 전우진, 이인범 2023.09.10
     */
    public User updateInfo(String userId, UserUpdateDTO userUpdateDTO){
        userResponse = new UserResponse();
        UserEntity user = userRepository.findById(userId).orElse(null);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 문자열 -> Date
        LocalDate date = LocalDate.parse(userUpdateDTO.getBirth(), formatter);

        if (user != null) {
            user.update(userUpdateDTO, date);
            userRepository.save(user);

            User u = new User(user.getId(), user.getName(), user.getPhone(), user.getBirth().toString());
            return u;
        } else {
            return null;
        }
    }

    /**
     * 사용자 정보 조회
     * - 전우진, 이인범 2023.09.10
     */
    public User getInfo(String userId){
        UserEntity user = userRepository.findById(userId).orElse(null);
        UserBody userBody = userBodyRepository.findById(user.getId()).orElse(null);
        TeamMember teamMember = teamMemberRepository.findByUserId(user.getId());

        String teamName = "";
        if(teamMember == null) {
            teamName = "사용자 팀 없음";
        } else teamName = teamMember.getTeam().getName();

        double BMI = Math.round(userBody.getWeight() / (userBody.getHeight() * userBody.getHeight()) * 10000 * 100) / 100.0;

        User u = new User(user.getId(), user.getPw(), user.getName(), user.getPhone(), user.getBirth().toString(), userBody.getHeight(), userBody.getWeight(), teamName, BMI);
        return u;
    }

    public UserEntity getUserInfo(String userId){
        log.info("getUserInfo 함수에 들어옴 -> " + userId);
        UserEntity user = userRepository.findById(userId).orElse(null);
        log.info("회원 조회 결과 -> " + user);

        return user;
    }

    /**
     * 사용자 조회
     * - 이인범
     */
    public UserEntity FindUser(String userId){
        UserEntity user = userRepository.findById(userId).orElse(null);
        return user;
    }

    /**
     * 비밀번호 재설정
     * - 전우진, 이인범 2023.09.11
     */
    public UserResponse passwordUpdate(UserPwConfirmDTO userPwConfirmDTO) {
        userResponse = new UserResponse();

        Optional<UserEntity> user = userRepository.findById(userPwConfirmDTO.getUserId());
        user.ifPresentOrElse(
                userEntity -> {
            userResponse.data.userId = userEntity.getId();

            if (userPwConfirmDTO.getNewPw().equals(userPwConfirmDTO.getNewConfirmPw())) {
                // TODO password update
                userEntity.setPw(userPwConfirmDTO.getNewPw());
                userRepository.save(userEntity);

                userResponse.data.code = userResponse.success;
                userResponse.data.message = "비밀번호 재설정 성공";
            } else{
                userResponse.data.message = "비밀번호 불일치";
                userResponse.data.code = userResponse.fail;
            }
        }, () -> {
               userResponse.data.message = "존재하지 않는 사용자";
               userResponse.data.code = userResponse.fail;
            }
        );
        return userResponse;
    }

    /**
     * 사용자 탈퇴
     * - 전우진 2023.09.10
     */
    public UserResponse withdrawalUser(String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        userResponse = new UserResponse();

        if(user != null) {
            userRepository.deleteById(userId);
            userResponse.data.userId = user.getId();
            userResponse.data.code = userResponse.success;
            userResponse.data.message = "사용자 탈퇴 완료";
        } else {
            return null;
        }
        return userResponse;
    }
}
