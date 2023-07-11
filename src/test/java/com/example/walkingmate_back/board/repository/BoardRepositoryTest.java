package com.example.walkingmate_back.board.repository;

import com.example.walkingmate_back.board.entity.Board;
import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 저장 테스트")
    public void createBoardTest() {
        Board board = new Board();
        UserEntity user = new UserEntity();
        user.setId("bbb");
        board.setTitle("테스트 제목");
        board.setContent("테스트 내용");
        board.setUser(user);

        Board savedBoard = boardRepository.save(board);
        System.out.println(savedBoard.toString());
    }
}
