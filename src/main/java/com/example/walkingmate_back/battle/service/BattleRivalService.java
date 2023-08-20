package com.example.walkingmate_back.battle.service;

import com.example.walkingmate_back.battle.dto.BattleRivalResponseDTO;
import com.example.walkingmate_back.battle.dto.BattleRivalUpdateDTO;
import com.example.walkingmate_back.battle.entity.Battle;
import com.example.walkingmate_back.battle.entity.BattleRival;
import com.example.walkingmate_back.battle.repository.BattleRepository;
import com.example.walkingmate_back.battle.repository.BattleRivalRepository;
import com.example.walkingmate_back.team.entity.TeamMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

/**
 *    대결 라이벌 저장, 걸음 수 수정
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.08.20
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class BattleRivalService {

    private final BattleRepository battleRepository;
    private final BattleRivalRepository battleRivalRepository;

    /**
     * 사용자, 팀 소속, 팀장, 대결 여부 확인 후 대결 라이벌 저장
     * - 전우진 2023.07.19
     */
    public BattleRivalResponseDTO saveBattleRival(Battle battle, TeamMember teamMember) {
        LocalDate lc = LocalDate.now();
        BattleRival result = battleRivalRepository.findByTeamId(teamMember.getTeam().getId());

        // 팀이 대결에 참여하지 않은 경우
        if(result == null) {
            BattleRival battleRival = new BattleRival(battle, teamMember.getTeam());
            battleRivalRepository.save(battleRival);

            battle.update(lc);
            battleRepository.save(battle);

            return BattleRivalResponseDTO.builder()
                    .teamId(battleRival.getTeam().getId())
                    .teamName(battleRival.getTeam().getName())
                    .peopleNum(battleRival.getTeam().getPeopleNum())
                    .step(battleRival.getStep())
                    .build();
        } else {
            // 팀이 대결에 참여한 경우
            return null;
        }
    }

    /**
     * 대결 확인 후 대결 라이벌 걸음 수 수정
     * - 전우진 2023.07.19
     */
    public BattleRivalResponseDTO updateBattleRival(BattleRivalUpdateDTO battleRivalUpdateDTO, Long battleId, TeamMember teamMember) {
        Battle battle = battleRepository.findById(battleId).orElse(null);

        if(battle == null) {
            // 대결이 존재하지 않는 경우
            return null;
        }

        BattleRival battleRival = battleRivalRepository.findByTeamId(teamMember.getTeam().getId());

        battleRival.update(battleRivalUpdateDTO);
        battleRivalRepository.save(battleRival);

        battle.update(battleRivalUpdateDTO);
        battleRepository.save(battle);

        return BattleRivalResponseDTO.builder()
                .teamId(battleRival.getTeam().getId())
                .teamName(battleRival.getTeam().getName())
                .peopleNum(battleRival.getTeam().getPeopleNum())
                .step(battleRival.getStep())
                .build();
    }
}
