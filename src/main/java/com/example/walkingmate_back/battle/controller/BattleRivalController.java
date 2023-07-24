package com.example.walkingmate_back.battle.controller;

import com.example.walkingmate_back.battle.dto.BattleRivalResponseDTO;
import com.example.walkingmate_back.battle.dto.BattleRivalUpdateDTO;
import com.example.walkingmate_back.battle.entity.Battle;
import com.example.walkingmate_back.battle.service.BattleRivalService;
import com.example.walkingmate_back.battle.service.BattleService;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.service.TeamMemberService;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 *    대결 라이벌 저장, 걸음 수 수정
 *
 *   @version          1.00 / 2023.07.24
 *   @author           전우진
 */


//@Controller
@RestController
@RequestMapping("/battle")
public class BattleRivalController {

    private final BattleRivalService battleRivalService;
    private final UserService userService;
    private final TeamMemberService teamMemberService;
    private final BattleService battleService;

    public BattleRivalController(BattleRivalService battleRivalService, UserService userService, TeamMemberService teamMemberService, BattleService battleService) {
        this.battleRivalService = battleRivalService;
        this.userService = userService;
        this.teamMemberService = teamMemberService;
        this.battleService = battleService;
    }

    // 대결 라이벌 저장
    @PostMapping("/battleRival/{battleId}")
    public ResponseEntity<DefaultRes<BattleRivalResponseDTO>> saveBattleRival(@PathVariable Long battleId,  Authentication authentication) {
        UserEntity user = userService.FindUser(authentication.getName());
        if(user == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);

        TeamMember teamMember = teamMemberService.FindTeam(user.getId());
        // 팀 소속이 없는 경우
        if(teamMember.getTeam().getId() == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);

        // 팀장이 아닌 경우
        if(teamMember.isTeamLeader() == false) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAMLEADER, null), HttpStatus.OK);

        // 대결이 존재하지 않는 경우
        Battle battle = battleService.FindBattle(battleId);
        if(battle == null)  return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BATTLE, null), HttpStatus.OK);

        BattleRivalResponseDTO battleRivalResponseDTO = battleRivalService.saveBattleRival(battle, teamMember);

        if(battleRivalResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_BATTLERIVAL, battleRivalResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.CHECK_TEAM_BATTLE, null), HttpStatus.OK);
    }

    // 대결 라이벌 수정 - 걸음 수
    @PutMapping("/battleRival/{battleId}")
    public ResponseEntity<DefaultRes<BattleRivalResponseDTO>> updateBattleRival(@RequestBody BattleRivalUpdateDTO battleRivalUpdateDTO, @PathVariable Long battleId, Authentication authentication) {
        UserEntity user = userService.FindUser(authentication.getName());
        if(user == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);

        TeamMember teamMember = teamMemberService.FindTeam(user.getId());
        BattleRivalResponseDTO battleRivalResponseDTO = battleRivalService.updateBattleRival(battleRivalUpdateDTO, battleId, teamMember);

        if(battleRivalResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.UPDATE_BATTLERIVAL, battleRivalResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BATTLE, null), HttpStatus.OK);
    }
}
