package com.example.walkingmate_back.user.service;

import com.example.walkingmate_back.user.dto.UserBodyResponseDTO;
import com.example.walkingmate_back.user.dto.UserBodyUpdateDTO;
import com.example.walkingmate_back.user.entity.UserBody;
import com.example.walkingmate_back.user.repository.UserBodyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *    사용자 신체정보 수정, 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.20
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
    public UserBodyResponseDTO getUserBody(String userId) {
        UserBody userBody = userBodyRepository.findById(userId).orElse(null);

        if(userBody != null) {  // 신체정보가 존재하는 경우

            return UserBodyResponseDTO.builder()
                    .userId(userBody.getUser().getId())
                    .height(userBody.getHeight())
                    .weight(userBody.getWeight())
                    .build();

        } else { // 신체정보가 존재하지 않는 경우
            return null;
        }

    }

    /**
     * 사용자 확인 후 신체정보 수정
     * - 전우진 2023.07.14
     */
    public UserBodyResponseDTO updateUserBody(UserBodyUpdateDTO userBodyUpdateDTO, String userId) {
        UserBody userBody = userBodyRepository.findById(userId).orElse(null);

        if(userBody == null) {  // 신체정보가 존재하지 않는 경우
            return null;
        }

        userBody.update(userBodyUpdateDTO);
        userBodyRepository.save(userBody);

        return UserBodyResponseDTO.builder()
                .userId(userBody.getUser().getId())
                .height(userBody.getHeight())
                .weight(userBody.getWeight())
                .build();

    }
}
