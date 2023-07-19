package com.example.walkingmate_back.battle.service;

import com.example.walkingmate_back.battle.dto.BattleRequestDTO;
import com.example.walkingmate_back.battle.dto.BattleResponseDTO;
import com.example.walkingmate_back.battle.dto.BattleRivalResponseDTO;
import com.example.walkingmate_back.battle.entity.Battle;
import com.example.walkingmate_back.battle.entity.BattleRival;
import com.example.walkingmate_back.battle.repository.BattleRepository;
import com.example.walkingmate_back.battle.repository.BattleRivalRepository;
import com.example.walkingmate_back.main.entity.Message;
import com.example.walkingmate_back.main.entity.StatusEnum;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.repository.TeamMemberRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
 *   @version          1.00 / 2023.07.19
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
    public ResponseEntity<Message> saveBattle(BattleRequestDTO battleRequestDTO, String userId) throws ParseException {
        UserEntity user = userRepository.findById(userId).orElse(null);
        // 문자열
        String dateStr = battleRequestDTO.getStartDate();
        // 포맷터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 문자열 -> Date
        LocalDate date = LocalDate.parse(dateStr, formatter);

        if(user.getTeam().getId() != null) { // 팀 소속이 있는 경우
            TeamMember teamMember = teamMemberRepository.findByUserId(userId);

            if(teamMember.isTeamLeader() == true) {  // 팀장인 경우

                BattleRival result = battleRivalRepository.findByTeamId(user.getTeam().getId());

                // 팀이 배틀을 생성하지 않은 경우
                if(result == null) {
                    Battle battle = new Battle(date);
                    battleRepository.save(battle);

                    BattleRival battleRival = new BattleRival(battle, teamMember.getTeam());
                    battleRivalRepository.save(battleRival);

                    Message message = new Message();
                    message.setStatus(StatusEnum.OK);
                    message.setMessage("성공 코드");
                    message.setData("대결 저장 성공");

                    return ResponseEntity.ok().body(message);
                } else {
                    // 팀이 배틀을 생성한 경우
                    return ResponseEntity.notFound().build();
                }
            } else {
                // 팀장이 아닌 경우
                return ResponseEntity.notFound().build();
            }
        } else {
            // 팀 소속이 없는 경우
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 대결 확인 후 대결 삭제
     * - 전우진 2023.07.17
     */
    public ResponseEntity<Message> deleteBattle(Long battleId) {
        Battle battle = battleRepository.findById(battleId).orElse(null);

        if(battle == null) {
            // 대결이 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

        // 대결상대 모두 삭제

        battleRepository.delete(battle);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("대결 삭제 성공");

        return ResponseEntity.ok().body(message);
    }

    /**
     * 대결 확인 후 대결 전체 조회
     * - 전우진 2023.07.17
     */
    public ResponseEntity<Message> getAllBattle() {
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

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(result);

        return ResponseEntity.ok().body(message);
    }

    /**
     * 대결 확인 후 단일 대결 조회
     * - 전우진 2023.07.17
     */

    public ResponseEntity<Message> getBattle(Long battleId) {
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

            BattleResponseDTO battleResponseDTO = new BattleResponseDTO(
                    battle.getId(),
                    battle.getStartDate(),
                    battle.getTotalStep(),
                    battleCheck,
                    battleRivalResponseDTOList
            );

            Message message = new Message();
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공 코드");
            message.setData(battleResponseDTO);

            return ResponseEntity.ok().body(message);
        } else {
            // 대결이 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }
    }


}
