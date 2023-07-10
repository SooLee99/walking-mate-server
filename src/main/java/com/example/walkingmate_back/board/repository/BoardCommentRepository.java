package com.example.walkingmate_back.board.repository;

import com.example.walkingmate_back.board.entity.BoardComment;
import com.example.walkingmate_back.board.entity.BoardCommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment, BoardCommentId> {

}
