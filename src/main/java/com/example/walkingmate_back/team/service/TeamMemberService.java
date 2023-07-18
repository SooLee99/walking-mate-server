package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.main.entity.Message;
import com.example.walkingmate_back.main.entity.StatusEnum;
import com.example.walkingmate_back.team.dto.TeamMemberRequestDTO;
import com.example.walkingmate_back.team.entity.Team;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.repository.TeamMemberRepository;
import com.example.walkingmate_back.team.repository.TeamRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *    멤버 가입, 나가기(삭제)
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.18
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    /**
     * 사용자, 팀 여부, 팀 확인 후 멤버 가입
     * - 전우진 2023.07.13
     */
    public ResponseEntity<Message> saveMember(Long teamId, TeamMemberRequestDTO teamMemberRequestDTO) {
        UserEntity user = userRepository.findById(teamMemberRequestDTO.getUserId()).orElse(null);

        if(user != null) { //사용자가 존재하는 경우
            if(user.getTeam() == null) { // 기존 팀이 없는 경우
                Team team = teamRepository.findById(teamId).orElse(null);
                if(team != null) { // 팀이 존재하는 경우
                    TeamMember teamMember = new TeamMember(user, team, false);
                    teamMemberRepository.save(teamMember);

                    // 사용자 팀 아이디 업데이트 해줘야함 - 팀 아이디 추가

                    Message message = new Message();
                    message.setStatus(StatusEnum.OK);
                    message.setMessage("성공 코드");
                    message.setData("멤버 가입 성공");

                    return ResponseEntity.ok().body(message);
                } else {
                    // 팀이 존재하지 않을 경우
                    return ResponseEntity.notFound().build();
                }
            } else {
                // 기존 팀이 있는 경우
                return ResponseEntity.notFound().build();
            }
        } else {
            // 사용자가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 팀 확인 후 멤버 나가기(삭제)
     * - 전우진 2023.07.13
     */
    public ResponseEntity<Message> deleteTeamMember(Long teamId, String userId) {
        Team team = teamRepository.findById(teamId).orElse(null);

        if(team == null) {
            // 팀이 존재하지 않을 경우
            return ResponseEntity.notFound().build();
        }

        TeamMember teamMember = teamMemberRepository.findByUserId(userId);
        teamMemberRepository.delete(teamMember);

        // 사용자 팀 아이디 null 값으로 업데이트

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("멤버 삭제 성공");

        return ResponseEntity.ok().body(message);
    }
}
