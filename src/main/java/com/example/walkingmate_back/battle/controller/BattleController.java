package com.example.walkingmate_back.battle.controller;

import com.example.walkingmate_back.battle.dto.BattleRequestDTO;
import com.example.walkingmate_back.battle.dto.BattleResponseDTO;
import com.example.walkingmate_back.battle.dto.BattleSearchDTO;
import com.example.walkingmate_back.battle.entity.BattleRival;
import com.example.walkingmate_back.battle.service.BattleRivalService;
import com.example.walkingmate_back.battle.service.BattleService;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.service.TeamMemberService;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

/**
 *    대결 생성, 삭제, 단일 조회, 전체 조회, 검색
 *
 *   @version          1.00 / 2023.08.31
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/battle")
public class BattleController {

    private final BattleService battleService;
    private final UserService userService;
    private final TeamMemberService teamMemberService;
    private final BattleRivalService battleRivalService;

    public BattleController(BattleService battleService, UserService userService, TeamMemberService teamMemberService, BattleRivalService battleRivalService) {
        this.battleService = battleService;
        this.userService = userService;
        this.teamMemberService = teamMemberService;
        this.battleRivalService = battleRivalService;
    }

    // 대결 생성
    @PostMapping("/new")
    public ResponseEntity<DefaultRes<BattleResponseDTO>> saveBattle(@RequestBody BattleRequestDTO battleRequestDTO, Authentication authentication) throws ParseException {
        UserEntity user = userService.FindUser(authentication.getName());
        if(user == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);

        TeamMember teamMember = teamMemberService.FindTeam(user.getId());
        // 팀 소속이 없는 경우
        if(teamMember == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);

        // 팀장이 아닌 경우
        if(teamMember.isTeamLeader() == false) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAMLEADER, null), HttpStatus.OK);

        BattleResponseDTO battleResponseDTO = battleService.saveBattle(battleRequestDTO, teamMember);

        if(battleResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_BATTLE, battleResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_WRITE_BATTLE, null), HttpStatus.OK);
    }

    // 대결 삭제
    @DeleteMapping("/{battleId}")
    public ResponseEntity<DefaultRes<BattleResponseDTO>> deleteBattle(@PathVariable Long battleId) throws ParseException {
        BattleResponseDTO battleResponseDTO = battleService.deleteBattle(battleId);

        if(battleResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.DELETE_BATTLE, battleResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BATTLE, null), HttpStatus.OK);
    }

    // 대결 전체 조회 - 대결 상대 포함
    @GetMapping("/list")
    public ResponseEntity<DefaultRes<List<BattleResponseDTO>>> listBatlle() throws ParseException {
        List<BattleResponseDTO> battleResponseDTO = battleService.getAllBattle();

        if(battleResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, battleResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BATTLE, null), HttpStatus.OK);
    }

    // 단일 대결 조회 - 대결 상대 포함
    @GetMapping("/{battleId}")
    public ResponseEntity<DefaultRes<BattleResponseDTO>> SpecificationBattle(@PathVariable Long battleId) throws ParseException {
        BattleResponseDTO battleResponseDTO = battleService.getBattle(battleId);

        if(battleResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, battleResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BATTLE, null), HttpStatus.OK);
    }

    // 대결 검색 조회
    @GetMapping("/list/search")
    public ResponseEntity<DefaultRes<List<BattleResponseDTO>>> listSearchBatlle(@RequestBody BattleSearchDTO battleSearchDTO) throws ParseException {
        List<BattleResponseDTO> battleResponseDTO = battleService.getSearchBattle(battleSearchDTO);

        if(battleResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, battleResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BATTLE, null), HttpStatus.OK);
    }

    // 대결 종료 - 삭제 예정
    /*@DeleteMapping("/finish/{battleId}")
    public ResponseEntity<DefaultRes<BattleResponseDTO>> finishBattle(@PathVariable Long battleId) throws ParseException {
        BattleResponseDTO battleResponseDTO = battleService.finishBattle(battleId);

        if(battleResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.DELETE_BATTLE, battleResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BATTLE, null), HttpStatus.OK);
    }*/

    // 현재 로그인된 사용자의 대결 조회 (팀 대결 상태 + 대결 아이디)
    @GetMapping("/teamStatus")
    public ResponseEntity<DefaultRes<BattleResponseDTO>> finishBattle(Authentication authentication) {

        TeamMember teamMember = teamMemberService.FindTeam(authentication.getName());
        if(teamMember == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);

        BattleRival battleRivalId = battleRivalService.FindBattleRival(teamMember.getTeam().getId());
        if(battleRivalId == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BATTLE, null), HttpStatus.OK);

        BattleResponseDTO battleResponseDTO = battleService.getUserBattle(battleRivalId);

        if(battleResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.DELETE_BATTLE, battleResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BATTLE, null), HttpStatus.OK);
    }
}
