package com.example.walkingmate_back.team.controller;

import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.team.dto.TeamMemberResponseDTO;
import com.example.walkingmate_back.team.service.TeamMemberService;
import com.example.walkingmate_back.team.service.TeamService;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 *    멤버 가입, 삭제
 *
 *   @version          1.00 / 2023.07.24
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/team")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;
    private final TeamService teamService;
    private final UserService userService;

    public TeamMemberController(TeamMemberService teamMemberService, TeamService teamService, UserService userService) {
        this.teamMemberService = teamMemberService;
        this.teamService = teamService;
        this.userService = userService;
    }

    // 멤버 가입
    @PostMapping("/{teamId}/member/save")
    public ResponseEntity<DefaultRes<TeamMemberResponseDTO>> saveMember(@PathVariable Long teamId, Authentication authentication){
        // 사용자 확인
        UserEntity user = userService.FindUser(authentication.getName());
        if(user == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);

        // 기존 팀이 없는 경우
        if(teamMemberService.FindTeam(user.getId()) == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);

        // 팀 확인
        if(teamService.FindTeam(teamId) == null)  return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);

        TeamMemberResponseDTO teamMemberResponseDTO = teamMemberService.saveMember(teamId, user);

        if(teamMemberResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_TEAMMEMBER, teamMemberResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.MAX_TEAM, null), HttpStatus.OK);
    }

    // 팀 멤버 삭제 - 강퇴 및 나가기
    @DeleteMapping("/{teamId}/member/{userId}")
    public ResponseEntity<DefaultRes<TeamMemberResponseDTO>> deleteTeamMember(@PathVariable Long teamId, @PathVariable String userId) {
        TeamMemberResponseDTO teamMemberResponseDTO = teamMemberService.deleteTeamMember(teamId, userId);

        if(teamMemberResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.DELETE_TEAMMEMBER, teamMemberResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);
    }


}
