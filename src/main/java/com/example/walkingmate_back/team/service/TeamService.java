package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.team.dto.TeamRequestDTO;
import com.example.walkingmate_back.team.entity.Team;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.repository.TeamMemberRepository;
import com.example.walkingmate_back.team.repository.TeamRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 *    팀 생성, 삭제
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.13
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    /**
     * 사용자 확인 후 팀 생성
     * - 전우진 2023.07.13
     */
    public int saveTeam(TeamRequestDTO teamRequestDTO, String userId) {
        Optional<UserEntity> user = userRepository.findById(userId);

        if(user != null) {  // 사용자 확인
            if(user.get().getTeam() == null) {  // 기존 팀이 없는 경우
                Team team = new Team(teamRequestDTO.getName(), teamRequestDTO.getPeopleNum(), teamRequestDTO.getState());
                teamRepository.save(team);

                // 팀 가입 - 리더
                TeamMember teamMember = new TeamMember(user.get(), team, true);
                teamMemberRepository.save(teamMember);
                return 1;
            } else {
                return -1; // 기존 팀이 이미 있으므로 생성 불가
            }
        } else { // 사용자 확인 불가
            return -2;
        }
    }

    /**
     * 팀 확인 후 팀 삭제
     * - 전우진 2023.07.13
     */
    public int deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);

        if(team == null) { // 팀 존재하지 않는 경우
            return -1;
        }

        teamRepository.delete(team);

        // 팀에 있는 멤버 모두 삭제

        return 1;
    }
}
