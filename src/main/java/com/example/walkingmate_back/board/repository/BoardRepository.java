package com.example.walkingmate_back.board.repository;

import com.example.walkingmate_back.board.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 게시글 전체 조회 - 최신 값부터
    @Query("SELECT d FROM Board d ORDER BY d.id DESC")
    List<Board> findAllBoard(Pageable pageable);

}
