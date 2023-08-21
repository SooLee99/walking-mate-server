package com.example.walkingmate_back.battle.service;

import com.example.walkingmate_back.battle.dto.BattleRequestDTO;
import com.example.walkingmate_back.battle.dto.BattleResponseDTO;
import com.example.walkingmate_back.battle.dto.BattleRivalResponseDTO;
import com.example.walkingmate_back.battle.dto.BattleSearchDTO;
import com.example.walkingmate_back.battle.entity.Battle;
import com.example.walkingmate_back.battle.entity.BattleRival;
import com.example.walkingmate_back.battle.repository.BattleRepository;
import com.example.walkingmate_back.battle.repository.BattleRivalRepository;
import com.example.walkingmate_back.team.entity.*;
import com.example.walkingmate_back.team.repository.TeamBattleHistoryRepository;
import com.example.walkingmate_back.team.repository.TeamMemberRepository;
import com.example.walkingmate_back.team.repository.TeamRankRepository;
import com.example.walkingmate_back.team.repository.TeamRepository;
import com.example.walkingmate_back.user.entity.UserRank;
import com.example.walkingmate_back.user.repository.UserRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *    대결 생성, 삭제, 단일 조회, 전체 조회, 검색, 종료
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.08.20
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class BattleService {

    private final BattleRepository battleRepository;
    private final BattleRivalRepository battleRivalRepository;
    private final TeamBattleHistoryRepository teamBattleHistoryRepository;
    private final UserRankRepository userRankRepository;
    private final TeamRankRepository teamRankRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 사용자, 팀 리더 확인 후 대결 생성
     * - 전우진 2023.07.17
     */
    public BattleResponseDTO saveBattle(BattleRequestDTO battleRequestDTO, TeamMember teamMember) throws ParseException {

        BattleRival result = battleRivalRepository.findByTeamId(teamMember.getTeam().getId());

        String battleCheck = "대결 팀 모집 중";
        // 팀이 대결을 생성하지 않은 경우
        if(result == null) {
            Battle battle = new Battle(format.parse(battleRequestDTO.getCreatedDate()));
            battleRepository.save(battle);

            BattleRival battleRival = new BattleRival(battle, teamMember.getTeam());
            battleRivalRepository.save(battleRival);

            return BattleResponseDTO.builder()
                    .id(battle.getId())
                    .startDate(battle.getStartDate())
                    .createdDate(format.format(battle.getCreatedDate()))
                    .totalStep(battle.getTotalStep())
                    .battleCheck(battleCheck)
                    .build();
        } else {
            // 팀이 대결을 생성한 경우
            return null;
        }
    }

    /**
     * 대결 확인 후 대결 삭제
     * - 전우진 2023.07.17
     */
    public BattleResponseDTO deleteBattle(Long battleId) throws ParseException {
        Battle battle = battleRepository.findById(battleId).orElse(null);

        if(battle == null) {
            // 대결이 존재하지 않는 경우
            return null;
        }

        if(battle.getBattleRivals().size() == 2) {
            Team team = teamRepository.findById(battle.getBattleRivals().get(0).getTeam().getId()).orElse(null);
            team.updateState("대결 팀 모집 중");
            teamRepository.save(team);

            team = teamRepository.findById(battle.getBattleRivals().get(1).getTeam().getId()).orElse(null);
            team.updateState("대결 팀 모집 중");
            teamRepository.save(team);
        } else {
            Team team = teamRepository.findById(battle.getBattleRivals().get(0).getTeam().getId()).orElse(null);
            team.updateState("대결 팀 모집 중");
            teamRepository.save(team);

        }

        // 대결상대 모두 삭제
        battleRepository.delete(battle);

        return BattleResponseDTO.builder()
                .id(battle.getId())
                .startDate(battle.getStartDate())
                .createdDate(format.format(battle.getCreatedDate()))
                .totalStep(battle.getTotalStep())
                .build();
    }

    /**
     * 대결 확인 후 대결 전체 조회
     * - 전우진 2023.07.17
     */
    public List<BattleResponseDTO> getAllBattle() throws ParseException {
        List<Battle> battles = battleRepository.findAll();
        List<BattleResponseDTO> result = new ArrayList<>();
        String battleCheck = "";

        for(Battle battle : battles) {

            List<BattleRivalResponseDTO> battleRivalResponseDTOList = battle.getBattleRivals().stream()
                    .map(battleRival -> new BattleRivalResponseDTO(battleRival.getTeam().getId(), battleRival.getTeam().getTeamMembers().get(0).getUser().getName(), battleRival.getTeam().getTeamRank().getTear(), battleRival.getTeam().getIntro(), battleRival.getTeam().getName(), battleRival.getTeam().getPeopleNum(), battleRival.getStep()))
                    .collect(Collectors.toList());

            if(battleRivalResponseDTOList.size() == 2) {
                battleCheck = "대결 진행 중";
            } else battleCheck = "대결 팀 모집 중";

            BattleResponseDTO battleResponseDTO = new BattleResponseDTO(
                    battle.getId(),
                    battle.getStartDate(),
                    format.format(battle.getCreatedDate()),
                    battle.getTotalStep(),
                    battleCheck,
                    battleRivalResponseDTOList
            );
            result.add(battleResponseDTO);
        }

        return result;
    }

    /**
     * 대결 확인 후 단일 대결 조회
     * - 전우진 2023.07.17
     */

    public BattleResponseDTO getBattle(Long battleId) throws ParseException {
        Battle battle = battleRepository.findById(battleId).orElse(null);

        if(battle != null) {  // 대결이 존재하는 경우
            String battleCheck = "";

            List<BattleRival> battleRivals = battle.getBattleRivals();

            List<BattleRivalResponseDTO> battleRivalResponseDTOList = battle.getBattleRivals().stream()
                    .map(battleRival -> new BattleRivalResponseDTO(battleRival.getTeam().getId(), battleRival.getTeam().getTeamMembers().get(0).getUser().getName(), battleRival.getTeam().getTeamRank().getTear(), battleRival.getTeam().getIntro(), battleRival.getTeam().getName(), battleRival.getTeam().getPeopleNum(), battleRival.getStep()))
                    .collect(Collectors.toList());

            if(battleRivalResponseDTOList.size() == 2) {
                battleCheck = "대결 진행 중";
            } else battleCheck = "대결 팀 모집 중";

            return BattleResponseDTO.builder()
                    .id(battle.getId())
                    .startDate(battle.getStartDate())
                    .createdDate(format.format(battle.getCreatedDate()))
                    .totalStep(battle.getTotalStep())
                    .battleCheck(battleCheck)
                    .battleRivalResponseDTOList(battleRivalResponseDTOList)
                    .build();
        } else {
            // 대결이 존재하지 않는 경우
            return null;
        }
    }

    /**
     * 대결 검색 조회
     * - 전우진 2023.07.23
     */
    public List<BattleResponseDTO> getSearchBattle(BattleSearchDTO battleSearchDTO) throws ParseException {
        List<Battle> battles = battleRepository.findAllByBattleRivalsByTeamName(battleSearchDTO.getSearch());
        List<BattleResponseDTO> result = new ArrayList<>();
        String battleCheck = "";

        for(Battle battle : battles) {

            List<BattleRivalResponseDTO> battleRivalResponseDTOList = battle.getBattleRivals().stream()
                    .map(battleRival -> new BattleRivalResponseDTO(battleRival.getTeam().getId(), battleRival.getTeam().getTeamMembers().get(0).getUser().getName(), battleRival.getTeam().getTeamRank().getTear(), battleRival.getTeam().getIntro(), battleRival.getTeam().getName(), battleRival.getTeam().getPeopleNum(), battleRival.getStep()))
                    .collect(Collectors.toList());


            if(battleRivalResponseDTOList.size() == 2) {
                battleCheck = "대결 진행 중";
            } else battleCheck = "대결 팀 모집 중";

            BattleResponseDTO battleResponseDTO = new BattleResponseDTO(
                    battle.getId(),
                    battle.getStartDate(),
                    format.format(battle.getCreatedDate()),
                    battle.getTotalStep(),
                    battleCheck,
                    battleRivalResponseDTOList
            );
            result.add(battleResponseDTO);
        }

        return result;
    }

    /**
     * 대결 종료
     * - 전우진 2023.07.31
     */
    public BattleResponseDTO finishBattle(Long battleId) throws ParseException {
        Battle battle = battleRepository.findById(battleId).orElse(null);

        if(battle == null) {
            // 대결이 존재하지 않는 경우
            return null;
        }

        LocalDate lc = LocalDate.now();
        int a = 0;
        // 승패여부에 따른 각 사용자 코인 수 증가
        // 무승부
        if(battle.getBattleRivals().get(0).getStep() == battle.getBattleRivals().get(1).getStep()) {
            TeamBattleHistory teamBattleHistory = new TeamBattleHistory(battle.getBattleRivals().get(1).getTeam(), lc, battle.getBattleRivals().get(1).getStep(), BattleHistoryEnum.TIE.toString());
            teamBattleHistoryRepository.save(teamBattleHistory);

            teamBattleHistory = new TeamBattleHistory(battle.getBattleRivals().get(0).getTeam(), lc, battle.getBattleRivals().get(0).getStep(), BattleHistoryEnum.TIE.toString());
            teamBattleHistoryRepository.save(teamBattleHistory);

            if(battle.getTotalStep() != 0) {

                int num = battle.getBattleRivals().get(0).getTeam().getPeopleNum() + battle.getBattleRivals().get(1).getTeam().getPeopleNum();
                int step = battle.getTotalStep() / 100;
                int coin = step / num;
                for (int i = 0; i < battle.getBattleRivals().get(0).getTeam().getTeamMembers().size(); i++) {
                    UserRank userRank = userRankRepository.findById(battle.getBattleRivals().get(0).getTeam().getTeamMembers().get(i).getUser().getId()).orElse(null);
                    userRank.update(coin);
                    userRankRepository.save(userRank);
                }
                for (int i = 0; i < battle.getBattleRivals().get(1).getTeam().getTeamMembers().size(); i++) {
                    UserRank userRank = userRankRepository.findById(battle.getBattleRivals().get(1).getTeam().getTeamMembers().get(i).getUser().getId()).orElse(null);
                    userRank.update(coin);
                    userRankRepository.save(userRank);
                }
            }
        } else {
            a = battle.getBattleRivals().get(0).getStep() > battle.getBattleRivals().get(1).getStep() ? 0 : 1;

            TeamBattleHistory teamBattleHistory = new TeamBattleHistory(battle.getBattleRivals().get(a).getTeam(), lc, battle.getBattleRivals().get(a).getStep(), BattleHistoryEnum.WIN.toString());
            teamBattleHistoryRepository.save(teamBattleHistory);

            TeamRank teamRank = teamRankRepository.findById(battle.getBattleRivals().get(a).getTeam().getId()).orElse(null);
            teamRank.updateWinNum();
            teamRankRepository.save(teamRank);

            teamBattleHistory = new TeamBattleHistory(battle.getBattleRivals().get(a == 0 ? 1 : 0).getTeam(), lc, battle.getBattleRivals().get(a == 0 ? 1 : 0).getStep(), BattleHistoryEnum.LOSE.toString());
            teamBattleHistoryRepository.save(teamBattleHistory);

            int step = battle.getTotalStep() / 100;
            int coin = step / battle.getBattleRivals().get(a).getTeam().getPeopleNum();
            for(int i=0; i<battle.getBattleRivals().get(a).getTeam().getTeamMembers().size(); i++) {
                UserRank userRank = userRankRepository.findById(battle.getBattleRivals().get(a).getTeam().getTeamMembers().get(i).getUser().getId()).orElse(null);
                userRank.update(coin);
                userRankRepository.save(userRank);
            }
        }

        Team team = teamRepository.findById(battle.getBattleRivals().get(0).getTeam().getId()).orElse(null);
        team.updateState("대결 팀 모집 중");
        teamRepository.save(team);

        team = teamRepository.findById(battle.getBattleRivals().get(1).getTeam().getId()).orElse(null);
        team.updateState("대결 팀 모집 중");
        teamRepository.save(team);

        // 대결상대 모두 삭제
        battleRepository.delete(battle);

        return BattleResponseDTO.builder()
                .id(battle.getId())
                .startDate(battle.getStartDate())
                .createdDate(format.format(battle.getCreatedDate()))
                .totalStep(battle.getTotalStep())
                .build();
    }

    public Battle FindBattle(Long battleId){
        return battleRepository.findById(battleId).orElse(null);
    }

}
