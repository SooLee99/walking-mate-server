package com.example.walkingmate_back.battle.repository;

import com.example.walkingmate_back.battle.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleRepository extends JpaRepository<Battle, Long> {

}
