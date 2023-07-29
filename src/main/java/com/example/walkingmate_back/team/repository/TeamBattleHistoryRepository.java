package com.example.walkingmate_back.team.repository;

import com.example.walkingmate_back.team.entity.TeamBattleHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamBattleHistoryRepository extends JpaRepository<TeamBattleHistory, Long> {

    List<TeamBattleHistory> findAllByTeamId(Long teamId);
}
