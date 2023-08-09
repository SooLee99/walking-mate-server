package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.team.dto.TeamRankResponseDTO;
import com.example.walkingmate_back.team.entity.TeamRank;
import com.example.walkingmate_back.team.repository.TeamRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 *    팀 전체 랭킹 조회, 티어 수정
 *
 *   @version          1.00 / 2023.08.09
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class TeamRankService {

    private final TeamRankRepository teamRankRepository;

    /**
     * 전체 랭킹 조회
     * - 전우진 2023.07.15
     */
    public List<TeamRankResponseDTO> getAllRank() {
        List<TeamRank> teamRanks = teamRankRepository.findAllByOrderByWinNumDesc();
        List<TeamRankResponseDTO> result = new ArrayList<>();

        for(TeamRank teamRank : teamRanks) {
            TeamRankResponseDTO rankResponseDTO = new TeamRankResponseDTO(
                    teamRank.getTeam().getId(),
                    teamRank.getCoin(),
                    teamRank.getTear(),
                    teamRank.getWinNum()
            );
            result.add(rankResponseDTO);
        }

        return result;
    }

    /**
     * 티어 수정
     * - 전우진 2023.08.09
     */
    public TeamRankResponseDTO updateTeamRank(Long teamId) {
        TeamRank teamRank = teamRankRepository.findById(teamId).orElse(null);
        int num = teamRank.getWinNum();

        // 아이언, 브론즈, 실버, 골드, 플래티넘, 다이아몬드, 마스터, 챌린저
        String tear;
        if(num > 1500) tear = "챌린저";  // 1,500
        else if(1500 > num && num > 999) tear = "마스터"; // 1,000 ~ 1,499
        else if(1000 > num && num > 499) tear = "다이아몬드";  // 500 ~ 999
        else if(500 > num && num > 99) tear = "플래티넘";  // 100 ~ 499
        else if(100 > num && num > 49) tear = "골드";  // 50 ~ 99
        else if(50 > num && num > 24) tear = "실버";  // 25 ~ 49
        else if(25 > num && num > 9) tear = "브론즈";  // 10 ~ 24
        else tear = "아이언";  // 0 ~ 9

        teamRank.updateTear(tear);
        teamRankRepository.save(teamRank);

        return TeamRankResponseDTO.builder()
                .teamId(teamId)
                .coin(teamRank.getCoin())
                .tear(teamRank.getTear())
                .winNum(teamRank.getWinNum())
                .build();
    }
}
