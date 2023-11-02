package com.example.walkingmate_back.battle.controller;

import com.example.walkingmate_back.battle.dto.BattleRequestDTO;
import com.example.walkingmate_back.battle.dto.BattleResponseDTO;
import com.example.walkingmate_back.battle.dto.BattleSearchDTO;
import com.example.walkingmate_back.battle.entity.Battle;
import com.example.walkingmate_back.battle.entity.BattleRival;
import com.example.walkingmate_back.battle.response.CustomBattleResponse;
import com.example.walkingmate_back.battle.service.BattleRivalService;
import com.example.walkingmate_back.battle.service.BattleService;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.team.dto.TeamMemberResponseDTO;
import com.example.walkingmate_back.team.entity.Team;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.service.TeamMemberService;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.*;

/**
 *    대결 생성, 삭제, 단일 조회, 전체 조회, 검색
 *
 *   @version          1.00 / 2023.08.31
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/battle")
@Slf4j
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
        log.info("일단 BattleController는 재대로 들어옴.");
    }

    // TODO: 대결 생성시 자동으로 생성 날짜와 시간이 저장될 수 있도록 수정하기
    // 대결 생성 신청 시 생성 날짜와 시간이 자동으로 저장될 수 있도록 코드를 수정함. (2023-09-16 이수 작성)
    @PostMapping("/new")
    public ResponseEntity<DefaultRes<BattleResponseDTO>> saveBattle(@RequestBody BattleRequestDTO battleRequestDTO, Authentication authentication) throws ParseException {
        log.info("대결 생성을 동작시킴");
        log.info(String.valueOf(battleRequestDTO));
        log.info(authentication.getName());

        UserEntity user = userService.FindUser(authentication.getName());
        if(user == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);

        TeamMember teamMember = teamMemberService.FindTeam(user.getId());
        // 팀 소속이 없는 경우
        if(teamMember == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);

        // 팀장이 아닌 경우
        if(!teamMember.isTeamLeader()) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAMLEADER, null), HttpStatus.OK);

        // 현재 날짜와 시간을 설정
        battleRequestDTO.setCreatedDate(new Date());

        BattleResponseDTO battleResponseDTO = battleService.saveBattle(battleRequestDTO, teamMember);

        if(battleResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_BATTLE, battleResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_WRITE_BATTLE, null), HttpStatus.OK);
    }

    // 대결 삭제
    @DeleteMapping("/{battleId}")
    public ResponseEntity<DefaultRes<BattleResponseDTO>> deleteBattle(@PathVariable Long battleId) throws ParseException {
        log.info("대결 삭제");
        BattleResponseDTO battleResponseDTO = battleService.deleteBattle(battleId);

        if(battleResponseDTO != null) {
            log.info("대결 삭제 성공");
            log.info(String.valueOf(battleResponseDTO));
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.DELETE_BATTLE, battleResponseDTO), HttpStatus.OK);
        } else {
            log.info("대결 삭제 실패");
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BATTLE, null), HttpStatus.OK);
        }
    }

    // 대결 전체 조회 - 대결 상대 포함
    @GetMapping("/list")
    public ResponseEntity<DefaultRes<List<BattleResponseDTO>>> listBatlle() throws ParseException {
        List<BattleResponseDTO> battleResponseDTO = battleService.getAllBattle();

        log.info("대결 전체 조회");
        log.info(battleResponseDTO.toString());

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
    // TODO: 현재 로그인된 사용자의 팀에 대한 정보와 대결에 대한 정보를 같이 반환합니다. (2023-09-16 이수)
    @GetMapping("/teamStatus")
    public ResponseEntity<DefaultRes<CustomBattleResponse>> finishBattle(Authentication authentication) {

        // TODO: 현재 팀에 소속되어 있는지 확인합니다. (2023-09-16 이수)
        TeamMember teamMember = teamMemberService.FindTeam(authentication.getName());
        log.info("현재 로그인된 사용자의 팀 소속 조회");
        log.info(String.valueOf(teamMember));
        log.info("현재 로그인된 사용자의 팀");
        //log.info(teamMember.toString());

        if(teamMember == null) {
            // TODO: 현재 팀에 소속되어 있지 않습니다. (2023-09-16 이수)
            log.info("현재 이용자는 팀에 소속되어 있지 않음.");
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, "팀 소속이 아님", null), HttpStatus.OK);
        } else {
            // TODO: 팀에 소속되어 있다면, 현재 팀이 대결 중인지를 알아봅니다. (2023-09-16 이수)
            log.info("현재 이용자는 팀에 소속되어 있음.");
            CustomBattleResponse customBattleResponse = new CustomBattleResponse();
            log.info(teamMember.toJson().toString());
            customBattleResponse.setUserTeamMember(teamMember.toJson());

            log.info("이제 해당 팀이 대결 중인지 확인하는 단계");
            BattleRival battleRivalId = battleRivalService.FindBattleRival(teamMember.getTeam().getId());
            BattleResponseDTO battleResponseDTO = battleService.getUserBattle(battleRivalId);
            customBattleResponse.setBattleResponses(battleResponseDTO);

            if (battleRivalId == null) {
                // TODO: 해당 팀은 현재 대결 중이 아닙니다. (2023-09-16 이수)
                log.info("해당 팀은 대결 모집 글을 안올린 상태입니다.");
                log.info(customBattleResponse.toString());
                return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, "팀 생성 완료", customBattleResponse), HttpStatus.OK);
            } else {
                // TODO: 해당 팀은 현재 대결 중입니다. (2023-09-16 이수)
                log.info("해당 팀은 대결 모집 글을 올린 상태입니다. (현재 상대 팀이 있는지는 api 결과를 통해서 확인 가능함.)");
                log.info(customBattleResponse.toString());
                customBattleResponse.setBattleResponses(battleResponseDTO);
                return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, "대결 생성 완료", customBattleResponse), HttpStatus.OK);
            }
        }
    }
}
