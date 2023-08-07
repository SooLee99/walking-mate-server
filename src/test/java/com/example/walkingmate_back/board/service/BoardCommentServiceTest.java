package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardCommentRequestDTO;
import com.example.walkingmate_back.board.dto.BoardCommentResponseDTO;
import com.example.walkingmate_back.board.dto.BoardCommentUpdateDTO;
import com.example.walkingmate_back.board.dto.BoardUpdateDTO;
import com.example.walkingmate_back.board.entity.BoardComment;
import com.example.walkingmate_back.user.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BoardCommentServiceTest {

    public UserEntity user() {
        UserEntity user = new UserEntity();
        user.setId("bbb");

        return user;
    }

    @Autowired
    BoardCommentService boardCommentService;

    @BeforeAll
    static void beforeAll() {
        System.out.println("## BeforeAll Annotation 호출 ##");
        System.out.println();
    }

    @AfterAll
    static void afterAll() {
        System.out.println("## afterAll Annotation 호출 ##");
        System.out.println();
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("## beforeEach Annotation 호출 ##");
        System.out.println();
    }

    @AfterEach
    void afterEach() {
        System.out.println("## afterEach Annotation 호출 ##");
        System.out.println();
    }

    @Test
    @DisplayName("댓글 저장 테스트")
    void saveCommentTest() {
        System.out.println("## saveCommentTest 시작 ##");
        System.out.println();

        BoardCommentRequestDTO boardCommentRequestDTO = new BoardCommentRequestDTO();
        boardCommentRequestDTO.setContent("commentContent1");

        BoardCommentResponseDTO saveComment = boardCommentService.saveCommemt(boardCommentRequestDTO, user(), 12L);

        assertEquals(saveComment.getBoardId(), 12L);
        assertEquals(saveComment.getUserId(), user().getId());
        assertEquals(saveComment.getContent(), boardCommentRequestDTO.getContent());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateCommentTest() {
        System.out.println("## updateCommentTest 시작 ##");
        System.out.println();

        BoardComment boardComment = boardCommentService.FindBoardComment(8L);
        BoardCommentUpdateDTO boardCommentUpdateDTO = new BoardCommentUpdateDTO();
        boardCommentUpdateDTO.setContent("commentContent1");

        BoardCommentResponseDTO updateComment = boardCommentService.updateComment(boardComment, boardCommentUpdateDTO, user().getId());

        assertEquals(updateComment.getId(), boardComment.getId());
        assertEquals(updateComment.getContent(), boardCommentUpdateDTO.getContent());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteCommentTest() {
        System.out.println("## deleteCommentTest 시작 ##");
        System.out.println();

        BoardComment boardComment = boardCommentService.FindBoardComment(8L);

        BoardCommentResponseDTO deleteComment = boardCommentService.deleteComment(boardComment, user().getId());

        assertEquals(deleteComment.getId(), boardComment.getId());
        assertEquals(deleteComment.getBoardId(), boardComment.getBoard().getId());
        assertEquals(deleteComment.getUserId(), user().getId());
        assertEquals(deleteComment.getContent(), boardComment.getContent());
    }

}