package com.example.walkingmate_back.battle.service;

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

/**
 *    대결 라이벌 저장
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.19
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class BattleRivalService {

    private final BattleRepository battleRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final BattleRivalRepository battleRivalRepository;

    /**
     * 사용자, 팀 소속, 팀장, 대결 여부 확인 후 대결 라이벌 저장
     * - 전우진 2023.07.19
     */
    public ResponseEntity<Message> saveBattleRival(Long battleId, String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if(user.getTeam() != null) { // 팀 소속이 있는 경우
            TeamMember teamMember = teamMemberRepository.findByUserId(userId);

            if (teamMember.isTeamLeader() == true) {  // 팀장인 경우
                Battle battle = battleRepository.findById(battleId).orElse(null);

                if(battle != null) {  // 대결이 존재하는 경우
                    BattleRival result = battleRivalRepository.findByTeamId(user.getTeam().getId());

                    // 팀이 대결에 참여하지 않은 경우
                    if(result == null) {
                        BattleRival battleRival = new BattleRival(battle, teamMember.getTeam());
                        battleRivalRepository.save(battleRival);

                        Message message = new Message();
                        message.setStatus(StatusEnum.OK);
                        message.setMessage("성공 코드");
                        message.setData("대결 저장 성공");

                        return ResponseEntity.ok().body(message);
                    } else {
                        // 팀이 대결에 참여한 경우
                        return ResponseEntity.notFound().build();
                    }
                } else {
                    // 대결이 존재하지 않는 경우
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
}
