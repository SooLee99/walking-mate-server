package com.example.walkingmate_back.history.service;

import com.example.walkingmate_back.history.dto.CheckListRequestDTO;
import com.example.walkingmate_back.history.dto.CheckListResponseDTO;
import com.example.walkingmate_back.history.entity.CheckList;
import com.example.walkingmate_back.history.repository.CheckListRepository;
import com.example.walkingmate_back.main.entity.Message;
import com.example.walkingmate_back.main.entity.StatusEnum;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *    체크리스트 등록, 수정, 삭제, 체크 및 해제, 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.18
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final UserRepository userRepository;

    /**
     * 사용자 확인 후 체크리스트 저장
     * - 전우진 2023.07.12
     */
    public ResponseEntity<Message> saveCheckList(CheckListRequestDTO checkListRequestDTO) {
        UserEntity user = userRepository.findById(checkListRequestDTO.getUserId()).orElse(null);
        LocalDate now = LocalDate.now();

        if(user != null) { // 사용자가 존재하는 경우
            CheckList checkList = new CheckList(user, now, checkListRequestDTO.getContent(), false);
            checkListRepository.save(checkList);

            Message message = new Message();
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공 코드");
            message.setData("체크리스트 저장 성공");

            return ResponseEntity.ok().body(message);
        } else {
            // 사용자가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * 체크리스트 탐색 후 체크리스트 수정
     * - 전우진 2023.07.12
     */
    public ResponseEntity<Message> updateCheckList(Long listId, CheckListRequestDTO checkListRequestDTO) {
        CheckList checkList = checkListRepository.findById(listId).orElse(null);
        LocalDate now = LocalDate.now();

        if(checkList == null) {
            // 체크리스트가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

        checkList.update(checkListRequestDTO, now);

        checkListRepository.save(checkList);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("체크리스트 수정 성공");

        return ResponseEntity.ok().body(message);
    }

    /**
     * 체크리스트 탐색 후 체크리스트 체크 및 해제
     * - 전우진 2023.07.12
     */
    public ResponseEntity<Message> updateCheckd(Long listId) {
        CheckList checkList = checkListRepository.findById(listId).orElse(null);

        if(checkList == null) {
            // 체크리스트가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

        boolean checked = checkList.isChecked();

        if(checked == true) {  // 체크리스트가 체크되어 있는 경우
            checkList.updateCheckd(false);

            checkListRepository.save(checkList);

            Message message = new Message();
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공 코드");
            message.setData("체크리스트 해제 성공");

            return ResponseEntity.ok().body(message);
        } else {  // 체크리스트가 체크되어 있지 않는 경우
            checkList.updateCheckd(true);

            checkListRepository.save(checkList);

            Message message = new Message();
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공 코드");
            message.setData("체크리스트 체크 성공");

            return ResponseEntity.ok().body(message);
        }
    }

    /**
     * 체크리스트 탐색 후 체크리스트 삭제
     * - 전우진 2023.07.12
     */
    public ResponseEntity<Message> deleteCheckList(Long listId) {
        CheckList checkList = checkListRepository.findById(listId).orElse(null);

        if (checkList == null) {
            // 체크리스트가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }
        checkListRepository.delete(checkList);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("체크리스트 삭제 성공");

        return ResponseEntity.ok().body(message);

    }

    /**
     * 체크리스트 탐색 후 체크리스트 조회
     * - 전우진 2023.07.13
     */
    public ResponseEntity<Message> getDateCheckList(String id, String date) {
        UserEntity user = userRepository.findById(id).orElse(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate lc = LocalDate.parse(date, formatter);

        List<CheckList> checkLists = checkListRepository.findByUserIdWithDate(user.getId(), lc);
        List<CheckListResponseDTO> result = new ArrayList<>();

        for (CheckList checkList : checkLists) {
            CheckListResponseDTO checkListResponseDTO = new CheckListResponseDTO(
                    checkList.getListId(),
                    checkList.getUser().getId(),
                    checkList.getDate().toString(),
                    checkList.isChecked(),
                    checkList.getContent()
            );
            result.add(checkListResponseDTO);
        }

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(result);

        return ResponseEntity.ok().body(message);

    }
}
