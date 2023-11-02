package com.example.walkingmate_back.team.repository;

import com.example.walkingmate_back.team.dto.TeamMemberResponseDTO;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.entity.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId> {

    TeamMember findByUserId(String userId);

    // 현재 이용자의 tema_member 테이블의 정보를 가져올 수 있도록 제작함. (2023-09-16 이수 작성.)
    //TeamMemberResponseDTO findAllByUserId(String userId);
}
