package com.example.walkingmate_back.battle.service;

import com.example.walkingmate_back.battle.dto.BattleResponseDTO;
import com.example.walkingmate_back.battle.dto.BattleRivalResponseDTO;
import com.example.walkingmate_back.battle.entity.Battle;
import com.example.walkingmate_back.battle.entity.BattleRival;
import com.example.walkingmate_back.battle.repository.BattleRepository;
import com.example.walkingmate_back.battle.repository.BattleRivalRepository;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.repository.TeamMemberRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *    대결 생성, 삭제, 단일 조회, 전체 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.17
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class BattleService {

    private final BattleRepository battleRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final BattleRivalRepository battleRivalRepository;

    /**
     * 사용자, 팀 리더 확인 후 대결 생성
     * - 전우진 2023.07.17
     */
    public int saveBattle(String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if(user.getTeam().getId() != null) {
            TeamMember teamMember = teamMemberRepository.findByUserId(userId);

            if(teamMember.isTeamLeader() == true) {
                Battle battle = new Battle();
                battleRepository.save(battle);

                BattleRival battleRival = new BattleRival(battle, teamMember.getTeam());
                battleRivalRepository.save(battleRival);

                return 1;
            } else { // 팀장이 아닌 경우
                return -2;
            }
        } else { // 팀 소속이 없는 경우
            return -1;
        }
    }

    /**
     * 대결 확인 후 대결 삭제
     * - 전우진 2023.07.17
     */
    public int deleteBattle(Long battleId) {
        Battle battle = battleRepository.findById(battleId).orElse(null);
        if(battle == null) {
            return -1;
        }

        // 대결상대 모두 삭제

        battleRepository.delete(battle);
        return 1;
    }

    /**
     * 대결 확인 후 대결 전체 조회
     * - 전우진 2023.07.17
     */
    public List<BattleResponseDTO> getAllBattle() {

        List<Battle> battles = battleRepository.findAll();
        List<BattleResponseDTO> result = new ArrayList<>();

        for(Battle battle : battles) {

            List<BattleRivalResponseDTO> battleRivalResponseDTOList = battle.getBattleRivals().stream()
                    .map(battleRival -> new BattleRivalResponseDTO(battleRival.getTeam().getId(), battleRival.getStep()))
                    .collect(Collectors.toList());

            BattleResponseDTO battleResponseDTO = new BattleResponseDTO(
                    battle.getId(),
                    battle.getStartDate(),
                    battle.getTotalStep(),
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

    public Optional<BattleResponseDTO> getBattle(Long battleId) {
        Optional<Battle> result = battleRepository.findById(battleId);

        if(result != null) {
            Battle battle = result.get();

            List<BattleRival> battleRivals = battle.getBattleRivals();

            List<BattleRivalResponseDTO> battleRivalResponseDTOList = battleRivals.stream()
                    .map(battleRival -> new BattleRivalResponseDTO(battleRival.getTeam().getId(), battleRival.getStep()))
                    .collect(Collectors.toList());

            return Optional.of(new BattleResponseDTO(
                    battle.getId(),
                    battle.getStartDate(),
                    battle.getTotalStep(),
                    battleRivalResponseDTOList
            ));
        } else {
            return Optional.empty();
        }
    }
}
