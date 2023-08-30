package com.example.walkingmate_back.board.entity;

import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@IdClass(RecommendCommentId.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recommendComment")
public class RecommendComment {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserEntity user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardCommentId")
    private BoardComment boardComment;

}
