package com.example.walkingmate_back.history.controller;

import com.example.walkingmate_back.history.dto.CheckListRequestDTO;
import com.example.walkingmate_back.history.service.CheckListService;
import org.springframework.web.bind.annotation.*;

/**
 *    체크리스트 등록, 수정, 삭제, 체크 및 해제
 *
 *   @version          1.00 / 2023.07.12
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/checkList")
public class CheckListController {

    private final CheckListService checkListService;

    public CheckListController(CheckListService checkListService) {
        this.checkListService = checkListService;
    }

    // 체크리스트 추가
    @PostMapping("/save")
    public int saveCheckList(@RequestBody CheckListRequestDTO checkListRequestDTO) {
        return checkListService.saveCheckList(checkListRequestDTO);
    }

    // 체크리스트 수정
    @PutMapping("/{listId}")
    public int updateCheckList(@PathVariable Long listId, @RequestBody CheckListRequestDTO checkListRequestDTO) {
        return checkListService.updateCheckList(listId, checkListRequestDTO);
    }

    // 체크리스트 삭제
    @DeleteMapping("/{listId}")
    public int deleteCheckList(@PathVariable Long listId) {
        return checkListService.deleteCheckList(listId);
    }


    // 체크리스트 체크 및 해제
    @PutMapping("/checked/{listId}")
    public int updateCheckd(@PathVariable Long listId) {
        return checkListService.updateCheckd(listId);
    }



}
