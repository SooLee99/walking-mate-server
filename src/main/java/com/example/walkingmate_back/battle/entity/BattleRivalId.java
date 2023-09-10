package com.example.walkingmate_back.battle.entity;

import com.example.walkingmate_back.team.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BattleRivalId implements Serializable {

    private Battle battle;
    private Team team;

}
