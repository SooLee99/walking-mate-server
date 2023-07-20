package com.example.walkingmate_back.battle.service;

import com.example.walkingmate_back.battle.dto.BattleRequestDTO;
import com.example.walkingmate_back.battle.dto.BattleResponseDTO;
import com.example.walkingmate_back.battle.dto.BattleRivalResponseDTO;
import com.example.walkingmate_back.battle.entity.Battle;
import com.example.walkingmate_back.battle.entity.BattleRival;
import com.example.walkingmate_back.battle.repository.BattleRepository;
import com.example.walkingmate_back.battle.repository.BattleRivalRepository;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *    대결 생성, 삭제, 단일 조회, 전체 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.20
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class BattleService {

    private final BattleRepository battleRepository;
    private final BattleRivalRepository battleRivalRepository;

    /**
     * 사용자, 팀 리더 확인 후 대결 생성
     * - 전우진 2023.07.17
     */
    public BattleResponseDTO saveBattle(BattleRequestDTO battleRequestDTO, TeamMember teamMember) throws ParseException {
        // 문자열
        String dateStr = battleRequestDTO.getStartDate();
        // 포맷터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 문자열 -> Date
        LocalDate date = LocalDate.parse(dateStr, formatter);

        BattleRival result = battleRivalRepository.findByTeamId(teamMember.getTeam().getId());

        // 팀이 대결을 생성하지 않은 경우
        if(result == null) {
            Battle battle = new Battle(date);
            battleRepository.save(battle);

            BattleRival battleRival = new BattleRival(battle, teamMember.getTeam());
            battleRivalRepository.save(battleRival);

            return BattleResponseDTO.builder()
                    .id(battle.getId())
                    .startDate(battle.getStartDate())
                    .totalStep(battle.getTotalStep())
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
    public BattleResponseDTO deleteBattle(Long battleId) {
        Battle battle = battleRepository.findById(battleId).orElse(null);

        if(battle == null) {
            // 대결이 존재하지 않는 경우
            return null;
        }

        // 대결상대 모두 삭제

        battleRepository.delete(battle);

        return BattleResponseDTO.builder()
                .id(battle.getId())
                .startDate(battle.getStartDate())
                .totalStep(battle.getTotalStep())
                .build();
    }

    /**
     * 대결 확인 후 대결 전체 조회
     * - 전우진 2023.07.17
     */
    public List<BattleResponseDTO> getAllBattle() {
        List<Battle> battles = battleRepository.findAll();
        List<BattleResponseDTO> result = new ArrayList<>();
        String battleCheck = "";

        for(Battle battle : battles) {

            List<BattleRivalResponseDTO> battleRivalResponseDTOList = battle.getBattleRivals().stream()
                    .map(battleRival -> new BattleRivalResponseDTO(battleRival.getTeam().getId(), battleRival.getTeam().getName(), battleRival.getTeam().getPeopleNum(), battleRival.getStep()))
                    .collect(Collectors.toList());

            if(battleRivalResponseDTOList.size() == 2) {
                battleCheck = "대결 진행 중";
            } else battleCheck = "대결 팀 모집 중";

            BattleResponseDTO battleResponseDTO = new BattleResponseDTO(
                    battle.getId(),
                    battle.getStartDate(),
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

    public BattleResponseDTO getBattle(Long battleId) {
        Battle battle = battleRepository.findById(battleId).orElse(null);

        if(battle != null) {  // 대결이 존재하는 경우
            String battleCheck = "";

            List<BattleRival> battleRivals = battle.getBattleRivals();

            List<BattleRivalResponseDTO> battleRivalResponseDTOList = battleRivals.stream()
                    .map(battleRival -> new BattleRivalResponseDTO(battleRival.getTeam().getId(), battleRival.getTeam().getName(), battleRival.getTeam().getPeopleNum(), battleRival.getStep()))
                    .collect(Collectors.toList());

            if(battleRivalResponseDTOList.size() == 2) {
                battleCheck = "대결 진행 중";
            } else battleCheck = "대결 팀 모집 중";

            return BattleResponseDTO.builder()
                    .id(battle.getId())
                    .startDate(battle.getStartDate())
                    .totalStep(battle.getTotalStep())
                    .battleCheck(battleCheck)
                    .battleRivalResponseDTOList(battleRivalResponseDTOList)
                    .build();
        } else {
            // 대결이 존재하지 않는 경우
            return null;
        }
    }

    public Battle FindBattle(Long battleId){
        return battleRepository.findById(battleId).orElse(null);
    }
}
