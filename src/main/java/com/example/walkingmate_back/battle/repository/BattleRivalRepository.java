package com.example.walkingmate_back.battle.repository;

import com.example.walkingmate_back.battle.entity.BattleRival;
import com.example.walkingmate_back.battle.entity.BattleRivalId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleRivalRepository extends JpaRepository<BattleRival, BattleRivalId> {

    BattleRival findByTeamId(Long teamId);
}
