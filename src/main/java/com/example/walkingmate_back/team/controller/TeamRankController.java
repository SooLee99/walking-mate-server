package com.example.walkingmate_back.team.controller;

import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.team.dto.TeamRankResponseDTO;
import com.example.walkingmate_back.team.service.TeamRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *    팀 전체 랭킹 조회, 티어 수정
 *
 *   @version          1.00 / 2023.08.09
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/team/teamRank")
@Slf4j
public class TeamRankController {

    private final TeamRankService teamRankService;

    public TeamRankController(TeamRankService teamRankService) {
        this.teamRankService = teamRankService;
    }

    // 전체 랭킹 조회
    @GetMapping("/list")
    public ResponseEntity<DefaultRes<List<TeamRankResponseDTO>>> SpecificationTeamRank() {
        List<TeamRankResponseDTO> teamRankResponseDTO =  teamRankService.getAllRank();
        log.info("전체 리스트 조회");
        log.info(teamRankResponseDTO.toString());

        if(teamRankResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_TEAMRANK, teamRankResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAMRANK, null), HttpStatus.OK);
    }

    // 티어 수정
    @PutMapping("/{teamId}")
    public ResponseEntity<DefaultRes<TeamRankResponseDTO>> updateTeamRank(@PathVariable Long teamId) {
        TeamRankResponseDTO teamRankResponseDTO = teamRankService.updateTeamRank(teamId);

        if(teamRankResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, teamRankResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USERRANK, null), HttpStatus.OK);
    }
}
