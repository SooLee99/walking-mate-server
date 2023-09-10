package com.example.walkingmate_back.team.repository;

import com.example.walkingmate_back.team.entity.TeamRank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TeamRankRepository extends JpaRepository<TeamRank, Long> {

    List<TeamRank> findAllByOrderByWinNumDesc();
}
