package com.example.walkingmate_back.team.controller;

import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.team.dto.TeamBattleRequestDTO;
import com.example.walkingmate_back.team.dto.TeamBattleResponseDTO;
import com.example.walkingmate_back.team.service.TeamBattleHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *    팀 대결 기록 저장, 전체 조회
 *
 *   @version          1.00 / 2023.07.29
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/team/battleHistory")
public class TeamBattleHistoryController {

    private final TeamBattleHistoryService teamBattleHistoryService;

    public TeamBattleHistoryController(TeamBattleHistoryService teamBattleHistoryService) {
        this.teamBattleHistoryService = teamBattleHistoryService;
    }

    // 팀 대결 기록 저장
    @PostMapping("/save")
    public ResponseEntity<DefaultRes<TeamBattleResponseDTO>> saveBattle(@RequestBody TeamBattleRequestDTO teamBattleRequestDTO) {
        TeamBattleResponseDTO teamBattleResponseDTO = teamBattleHistoryService.saveBattle(teamBattleRequestDTO);

        if(teamBattleResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_TEAMBATTLE, teamBattleResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);
    }

    // 팀 대결 기록 전체 조회
    @GetMapping("/list/{teamId}")
    public ResponseEntity<DefaultRes<List<TeamBattleResponseDTO>>> listTeamBattle(@PathVariable Long teamId) {
        List<TeamBattleResponseDTO> teamBattleResponseDTO = teamBattleHistoryService.getTeamBattle(teamId);

        if(teamBattleResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, teamBattleResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);
    }
}
