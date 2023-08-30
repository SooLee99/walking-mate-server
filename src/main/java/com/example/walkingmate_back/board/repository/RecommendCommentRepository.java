package com.example.walkingmate_back.board.repository;

import com.example.walkingmate_back.board.entity.RecommendComment;
import com.example.walkingmate_back.board.entity.RecommendCommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendCommentRepository extends JpaRepository<RecommendComment, RecommendCommentId> {

    boolean existsByBoardCommentIdAndUserId(Long id, String userId);

    void deleteByBoardCommentIdAndUserId(Long id, String userId);
}
