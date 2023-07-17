package com.example.walkingmate_back.battle.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"battleRivals"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "battle")
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id; // 대결 번호 (자동 증가)

    @Column
    private LocalDate startDate;  // 시작 날짜

    @Column
    private int totalStep;  // 대결 걸음 수

    @OneToMany(mappedBy = "battle", orphanRemoval = true)
    private List<BattleRival> battleRivals;

}
