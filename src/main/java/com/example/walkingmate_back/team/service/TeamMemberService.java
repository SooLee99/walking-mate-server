package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.team.dto.TeamMemberResponseDTO;
import com.example.walkingmate_back.team.entity.Team;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.repository.TeamMemberRepository;
import com.example.walkingmate_back.team.repository.TeamRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *    멤버 가입, 나가기(삭제)
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.20
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
    public TeamMemberResponseDTO saveMember(Long teamId, UserEntity user) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if(team != null) { // 팀이 존재하는 경우
            TeamMember teamMember = new TeamMember(user, team, false);
            teamMemberRepository.save(teamMember);

            // 사용자 팀 아이디 업데이트 해줘야함 - 팀 아이디 추가
            user.update(team);
            userRepository.save(user);

            return TeamMemberResponseDTO.builder()
                    .userId(teamMember.getUser().getId())
                    .teamId(teamMember.getTeam().getId())
                    .teamLeader(teamMember.isTeamLeader())
                    .build();
        } else {
            // 팀이 존재하지 않을 경우
            return null;
        }
    }

    /**
     * 팀 확인 후 멤버 나가기(삭제)
     * - 전우진 2023.07.13
     */
    public TeamMemberResponseDTO deleteTeamMember(Long teamId, String userId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        UserEntity user = userRepository.findById(userId).orElse(null);

        if(team == null) {
            // 팀이 존재하지 않을 경우
            return null;
        }

        TeamMember teamMember = teamMemberRepository.findByUserId(userId);
        teamMemberRepository.delete(teamMember);

        // 사용자 팀 아이디 null 값으로 업데이트
        user.deleteTeamMember(null);
        userRepository.save(user);

        return TeamMemberResponseDTO.builder()
                .userId(teamMember.getUser().getId())
                .teamId(teamMember.getTeam().getId())
                .teamLeader(teamMember.isTeamLeader())
                .build();
    }

    public TeamMember FindTeam(String userId){
        return teamMemberRepository.findByUserId(userId);
    }
}
