package com.example.walkingmate_back.battle.controller;

import com.example.walkingmate_back.battle.dto.BattleResponseDTO;
import com.example.walkingmate_back.battle.service.BattleService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 *    대결 생성, 삭제, 단일 조회, 전체 조회
 *
 *   @version          1.00 / 2023.07.17
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/battle")
public class BattleController {

    private final BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    // 대결 생성
    @PostMapping("/new")
    public int saveBattle(){
        String userId = "aaa";

        return battleService.saveBattle(userId);
    }

    // 대결 삭제
    @DeleteMapping("/{battleId}")
    public int deleteBattle(@PathVariable Long battleId) {
        return battleService.deleteBattle(battleId);
    }

    // 대결 전체 조회 - 대결 상대 포함
    @GetMapping("/list")
    public List<BattleResponseDTO> listBatlle() {
        return battleService.getAllBattle();
    }

    // 단일 대결 조회 - 대결 상대 포함
    @GetMapping("/{battleId}")
    public Optional<BattleResponseDTO> SpecificationBattle(@PathVariable Long battleId) {

        return battleService.getBattle(battleId);
    }
}
