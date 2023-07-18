package com.example.walkingmate_back.team.controller;

import com.example.walkingmate_back.main.entity.Message;
import com.example.walkingmate_back.team.dto.TeamRequestDTO;
import com.example.walkingmate_back.team.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *    팀 생성, 삭제, 단일 조회, 전체 조회, 가입된 팀 정보 조회
 *
 *   @version          1.00 / 2023.07.18
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // 팀 생성
    @PostMapping("/save")
    public ResponseEntity<Message> saveTeam(@RequestBody TeamRequestDTO teamRequestDTO){
        String userId = "aaa";

        return teamService.saveTeam(teamRequestDTO, userId);
    }

    // 팀 삭제
    @DeleteMapping("/{teamId}")
    public ResponseEntity<Message> deleteTeam(@PathVariable Long teamId) {
        return teamService.deleteTeam(teamId);
    }

    // 단일 팀 조회 - 멤버 포함
    @GetMapping("/{teamId}")
    public ResponseEntity<Message> SpecificationTeam(@PathVariable Long teamId) {

        return teamService.getTeam(teamId);
    }

    // 팀 전체 조회 - 멤버 포함
    @GetMapping("/list")
    public ResponseEntity<Message> listTeam() {
        return teamService.getAllTeam();
    }

    // 가입된 팀 정보 조회 - 랭킹 포함
    @GetMapping("/list/userTeam")
    public ResponseEntity<Message> SpecificationUserTeam() {
        String userId = "aaa";

        return teamService.getUserTeam(userId);
    }
}
