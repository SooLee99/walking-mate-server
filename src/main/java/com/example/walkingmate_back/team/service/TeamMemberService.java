package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.team.dto.TeamMemberRequestDTO;
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
 *    멤버 가입
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.13
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
    public int saveMember(Long teamId, TeamMemberRequestDTO teamMemberRequestDTO) {
        Optional<UserEntity> user = userRepository.findById(teamMemberRequestDTO.getUserId());

        if(user != null) { //사용자 확인
            if(user.get().getTeam() == null) { // 기존 팀이 없는 경우
                Optional<Team> team = teamRepository.findById(teamId);
                if(team != null) { // 팀 존재 확인
                    TeamMember teamMember = new TeamMember(user.get(), team.get(), false);
                    teamMemberRepository.save(teamMember);

                    // 사용자 팀 아이디 업데이트 해줘야함 - 팀 아이디 추가

                    return 1;
                } else { // 팀 존재하지 않을 경우
                    return -1;
                }
            } else {
                return -2; // 기존 팀 있음
            }
        } else {
            return -3; // 사용자 확인 불가
        }
    }


}
