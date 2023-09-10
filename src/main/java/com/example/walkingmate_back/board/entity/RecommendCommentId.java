package com.example.walkingmate_back.board.entity;

import com.example.walkingmate_back.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCommentId implements Serializable {
    private UserEntity user;
    private BoardComment boardComment;
}
