package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.team.dto.TeamBattleRequestDTO;
import com.example.walkingmate_back.team.dto.TeamBattleResponseDTO;
import com.example.walkingmate_back.team.entity.Team;
import com.example.walkingmate_back.team.entity.TeamBattleHistory;
import com.example.walkingmate_back.team.repository.TeamBattleHistoryRepository;
import com.example.walkingmate_back.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *    팀 대결 기록 저장, 전체 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.29
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class TeamBattleHistoryService {

    private final TeamBattleHistoryRepository teamBattleHistoryRepository;
    private final TeamRepository teamRepository;

    /**
     * 팀 확인 후 대결 기록 저장
     * - 전우진 2023.07.29
     */
    public TeamBattleResponseDTO saveBattle(TeamBattleRequestDTO teamBattleRequestDTO) {
        Team team = teamRepository.findById(teamBattleRequestDTO.getTeam().getId()).orElse(null);
        // 문자열
        String dateStr = teamBattleRequestDTO.getBattleDate();
        // 포맷터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 문자열 -> Date
        LocalDate date = LocalDate.parse(dateStr, formatter);

        if(team != null) {
            TeamBattleHistory teamBattleHistory = new TeamBattleHistory(team, date, teamBattleRequestDTO.getBetStep(), teamBattleRequestDTO.getVictory());
            teamBattleHistoryRepository.save(teamBattleHistory);
            return TeamBattleResponseDTO.builder()
                    .id(teamBattleHistory.getId())
                    .team(teamBattleRequestDTO.getTeam())
                    .battleDate(teamBattleHistory.getBattleDate())
                    .betStep(teamBattleHistory.getBetStep())
                    .victory(teamBattleRequestDTO.getVictory())
                    .build();
        } else {
            // 기존 팀이 이미 있으므로 생성 불가
            return null;
        }
    }

    /**
     * 팀별 대결 기록 전체 조회
     * - 전우진 2023.07.29
     */
    public List<TeamBattleResponseDTO> getTeamBattle(Long teamId) {

        List<TeamBattleHistory> teamBattleHistories = teamBattleHistoryRepository.findAllByTeamId(teamId);
        List<TeamBattleResponseDTO> result = new ArrayList<>();

        for(TeamBattleHistory teamBattleHistory : teamBattleHistories) {

            TeamBattleResponseDTO teamBattleResponseDTO = new TeamBattleResponseDTO(
                    teamBattleHistory.getId(),
                    teamBattleHistory.getTeam(),
                    teamBattleHistory.getBattleDate(),
                    teamBattleHistory.getVictory().toString(),
                    teamBattleHistory.getBetStep()
            );
            result.add(teamBattleResponseDTO);
        }
        return result;
    }
}
