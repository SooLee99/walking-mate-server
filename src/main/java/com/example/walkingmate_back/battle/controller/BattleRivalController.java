package com.example.walkingmate_back.battle.controller;

import com.example.walkingmate_back.battle.service.BattleRivalService;
import com.example.walkingmate_back.main.entity.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *    대결 라이벌 저장
 *
 *   @version          1.00 / 2023.07.19
 *   @author           전우진
 */


//@Controller
@RestController
@RequestMapping("/battle")
public class BattleRivalController {

    private final BattleRivalService battleRivalService;

    public BattleRivalController(BattleRivalService battleRivalService) {
        this.battleRivalService = battleRivalService;
    }

    // 대결 라이벌 저장
    @PostMapping("/battleRival/{battleId}")
    public ResponseEntity<Message> saveBattleRival(@PathVariable Long battleId) {
        String userId = "aaa";

        return battleRivalService.saveBattleRival(battleId, userId);
    }
}
