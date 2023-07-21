package com.example.walkingmate_back.team.controller;

import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.team.dto.TeamRequestDTO;
import com.example.walkingmate_back.team.dto.TeamResponseDTO;
import com.example.walkingmate_back.team.service.TeamService;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *    팀 생성, 삭제, 단일 조회, 전체 조회, 가입된 팀 정보 조회
 *
 *   @version          1.00 / 2023.07.21
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    public TeamController(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    // 팀 생성
    @PostMapping("/save")
    public ResponseEntity<DefaultRes<TeamResponseDTO>> saveTeam(@RequestBody TeamRequestDTO teamRequestDTO){
        String userId = "aaa";
        UserEntity user = userService.FindUser(userId);
        if(user == null)  return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);

        TeamResponseDTO teamResponseDTO = teamService.saveTeam(teamRequestDTO, user);

        if(teamResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_TEAM, teamResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_WRITE_TEAM, null), HttpStatus.OK);
    }

    // 팀 삭제
    @DeleteMapping("/{teamId}")
    public ResponseEntity<DefaultRes<TeamResponseDTO>> deleteTeam(@PathVariable Long teamId) {
        TeamResponseDTO teamResponseDTO = teamService.deleteTeam(teamId);

        if(teamResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.DELETE_TEAM, teamResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);
    }

    // 단일 팀 조회 - 멤버 포함
    @GetMapping("/{teamId}")
    public ResponseEntity<DefaultRes<TeamResponseDTO>> SpecificationTeam(@PathVariable Long teamId) {
        TeamResponseDTO teamResponseDTO = teamService.getTeam(teamId);

        if(teamResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, teamResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);
    }

    // 팀 전체 조회 - 멤버 포함
    @GetMapping("/list")
    public ResponseEntity<DefaultRes<List<TeamResponseDTO>>> listTeam() {
        List<TeamResponseDTO> teamResponseDTO = teamService.getAllTeam();

        if(teamResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, teamResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);
    }

    // 가입된 팀 정보 조회 - 랭킹 포함
    @GetMapping("/list/userTeam")
    public ResponseEntity<DefaultRes<TeamResponseDTO>> SpecificationUserTeam() {
        String userId = "aaa";
        TeamResponseDTO teamResponseDTO = teamService.getUserTeam(userId);

        if(teamResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, teamResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAM, null), HttpStatus.OK);
    }
}
