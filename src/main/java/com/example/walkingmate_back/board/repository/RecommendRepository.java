package com.example.walkingmate_back.board.repository;

import com.example.walkingmate_back.board.entity.Recommend;
import com.example.walkingmate_back.board.entity.RecommendId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, RecommendId> {

    boolean existsByBoardIdAndUserId(Long id, String userId);

    void deleteByBoardIdAndUserId(Long id, String userId);
}
