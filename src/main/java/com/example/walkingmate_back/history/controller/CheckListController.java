package com.example.walkingmate_back.history.controller;

import com.example.walkingmate_back.history.dto.CheckListRequestDTO;
import com.example.walkingmate_back.history.dto.CheckListResponseDTO;
import com.example.walkingmate_back.history.service.CheckListService;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import com.example.walkingmate_back.user.service.UserBodyService;
import com.example.walkingmate_back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *    체크리스트 등록, 수정, 삭제, 체크 및 해제, 조회
 *
 *   @version          1.00 / 2023.07.21
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/checkList")
public class CheckListController {

    private final CheckListService checkListService;
    private final UserService userService;

    public CheckListController(CheckListService checkListService, UserService userService) {
        this.checkListService = checkListService;
        this.userService = userService;
    }

    // 체크리스트 추가
    @PostMapping("/save")
    public ResponseEntity<DefaultRes<CheckListResponseDTO>> saveCheckList(@RequestBody CheckListRequestDTO checkListRequestDTO, Authentication authentication) {
        CheckListResponseDTO checkListResponseDTO = checkListService.saveCheckList(checkListRequestDTO, authentication.getName());

        if(checkListResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_CHECKLIST, checkListResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);
    }

    // 체크리스트 수정
    @PutMapping("/{listId}")
    public ResponseEntity<DefaultRes<CheckListResponseDTO>> updateCheckList(@PathVariable Long listId, @RequestBody CheckListRequestDTO checkListRequestDTO) {
        CheckListResponseDTO checkListResponseDTO = checkListService.updateCheckList(listId, checkListRequestDTO);

        if(checkListResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.UPDATE_CHECKLIST, checkListResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_CHECKLIST, null), HttpStatus.OK);
    }

    // 체크리스트 삭제
    @DeleteMapping("/{listId}")
    public ResponseEntity<DefaultRes<CheckListResponseDTO>> deleteCheckList(@PathVariable Long listId) {
        CheckListResponseDTO checkListResponseDTO = checkListService.deleteCheckList(listId);

        if(checkListResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.DELETE_CHECKLIST, checkListResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_CHECKLIST, null), HttpStatus.OK);
    }


    // 체크리스트 체크 및 해제
    @PutMapping("/checked/{listId}")
    public ResponseEntity<DefaultRes<CheckListResponseDTO>> updateCheckd(@PathVariable Long listId) {
        CheckListResponseDTO checkListResponseDTO = checkListService.updateCheckd(listId);

        if(checkListResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.CHECK_CHECKLIST, checkListResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_CHECKLIST, null), HttpStatus.OK);
    }

    // 체크리스트 조회 - 날짜별
    @GetMapping("/list/{date}")
    public ResponseEntity<DefaultRes<List<CheckListResponseDTO>>> listCheckList(@PathVariable String date, Authentication authentication) {
        UserEntity user = userService.FindUser(authentication.getName());

        List<CheckListResponseDTO> checkListResponseDTO = checkListService.getDateCheckList(user.getId(), date);

        if(checkListResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, checkListResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_CHECKLIST, null), HttpStatus.OK);
    }

}
