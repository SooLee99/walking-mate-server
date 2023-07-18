package com.example.walkingmate_back.user.service;

import com.example.walkingmate_back.main.entity.Message;
import com.example.walkingmate_back.main.entity.StatusEnum;
import com.example.walkingmate_back.user.dto.UserBodyResponseDTO;
import com.example.walkingmate_back.user.dto.UserBodyUpdateDTO;
import com.example.walkingmate_back.user.entity.UserBody;
import com.example.walkingmate_back.user.repository.UserBodyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *    사용자 신체정보 수정, 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.18
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class UserBodyService {

    private final UserBodyRepository userBodyRepository;

    /**
     * 사용자 확인 후 신체정보 조회
     * - 전우진 2023.07.14
     */
    public ResponseEntity<Message> getUserBody(String userId) {
        UserBody userBody = userBodyRepository.findById(userId).orElse(null);

        if(userBody != null) {  // 신체정보가 존재하는 경우

            UserBodyResponseDTO userBodyResponseDTO = new UserBodyResponseDTO(
                    userBody.getUserId(),
                    userBody.getWeight(),
                    userBody.getHeight()
            );

            Message message = new Message();
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공 코드");
            message.setData(userBodyResponseDTO);

            return ResponseEntity.ok().body(message);
        } else { // 신체정보가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * 사용자 확인 후 신체정보 수정
     * - 전우진 2023.07.14
     */
    public ResponseEntity<Message> updateUserBody(UserBodyUpdateDTO userBodyUpdateDTO, String userId) {
        UserBody userBody = userBodyRepository.findById(userId).orElse(null);

        if(userBody == null) {  // 신체정보가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

        userBody.update(userBodyUpdateDTO);

        userBodyRepository.save(userBody);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("신체정보 수정 성공");

        return ResponseEntity.ok().body(message);

    }
}
