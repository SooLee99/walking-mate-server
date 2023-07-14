package com.example.walkingmate_back.user.service;

import com.example.walkingmate_back.user.dto.UserBodyResponseDTO;
import com.example.walkingmate_back.user.dto.UserBodyUpdateDTO;
import com.example.walkingmate_back.user.entity.UserBody;
import com.example.walkingmate_back.user.repository.UserBodyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 *    사용자 신체정보 수정, 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.14
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
    public Optional<UserBodyResponseDTO> getUserBody(String userId) {
        Optional<UserBody> result = userBodyRepository.findById(userId);

        if(result.isPresent()) {
            UserBody userBody = result.get();

            return Optional.of(new UserBodyResponseDTO(
                    userBody.getUserId(),
                    userBody.getWeight(),
                    userBody.getHeight()
            ));
        } else {
            return Optional.empty();
        }

    }

    /**
     * 사용자 확인 후 신체정보 수정
     * - 전우진 2023.07.14
     */
    public int updateUserBody(UserBodyUpdateDTO userBodyUpdateDTO, String userId) {
        Optional<UserBody> result = userBodyRepository.findById(userId);

        if(result == null) {
            return -1;
        }

        UserBody userBody = result.get();
        userBody.update(userBodyUpdateDTO);

        userBodyRepository.save(userBody);
        return 1;

    }
}
