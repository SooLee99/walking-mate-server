package com.example.walkingmate_back.history.controller;

import com.example.walkingmate_back.history.dto.CheckListRequestDTO;
import com.example.walkingmate_back.history.service.CheckListService;
import com.example.walkingmate_back.main.entity.Message;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *    체크리스트 등록, 수정, 삭제, 체크 및 해제, 조회
 *
 *   @version          1.00 / 2023.07.18
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/checkList")
public class CheckListController {

    private final CheckListService checkListService;
    private final UserRepository userRepository;

    public CheckListController(CheckListService checkListService, UserRepository userRepository) {
        this.checkListService = checkListService;
        this.userRepository = userRepository;
    }

    // 체크리스트 추가
    @PostMapping("/save")
    public ResponseEntity<Message> saveCheckList(@RequestBody CheckListRequestDTO checkListRequestDTO) {
        return checkListService.saveCheckList(checkListRequestDTO);
    }

    // 체크리스트 수정
    @PutMapping("/{listId}")
    public ResponseEntity<Message> updateCheckList(@PathVariable Long listId, @RequestBody CheckListRequestDTO checkListRequestDTO) {
        return checkListService.updateCheckList(listId, checkListRequestDTO);
    }

    // 체크리스트 삭제
    @DeleteMapping("/{listId}")
    public ResponseEntity<Message> deleteCheckList(@PathVariable Long listId) {
        return checkListService.deleteCheckList(listId);
    }


    // 체크리스트 체크 및 해제
    @PutMapping("/checked/{listId}")
    public ResponseEntity<Message> updateCheckd(@PathVariable Long listId) {
        return checkListService.updateCheckd(listId);
    }

    // 체크리스트 조회 - 날짜별
    @GetMapping({"/list/{date}"})
    public ResponseEntity<Message> listCheckList(@PathVariable String date) {
        String nickName = "aaa";
        UserEntity user = userRepository.findById(nickName).orElse(null);

        return checkListService.getDateCheckList(user.getId(), date);
    }

}
