package com.example.walkingmate_back.battle.repository;

import com.example.walkingmate_back.battle.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BattleRepository extends JpaRepository<Battle, Long> {

    // 팀 이름으로 검색
    @Query("select a, b from Battle a left join BattleRival b on a.id = b.battle.id where b.team.name LIKE %:teamName%")
    List<Battle> findAllByBattleRivalsByTeamName(@Param("teamName")String teamName);
}
