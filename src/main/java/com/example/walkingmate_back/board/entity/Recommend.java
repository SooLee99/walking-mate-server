package com.example.walkingmate_back.board.entity;

import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@IdClass(RecommendId.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recommend")
public class Recommend {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserEntity user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;
}
