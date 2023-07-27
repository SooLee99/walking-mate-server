package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardRequestDTO;
import com.example.walkingmate_back.board.dto.BoardResponseDTO;
import com.example.walkingmate_back.board.dto.BoardUpdateDTO;
import com.example.walkingmate_back.board.entity.Board;
import com.example.walkingmate_back.board.repository.BoardRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

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
    @DisplayName("게시글 저장 테스트")
    public void createBoardTest() {
        System.out.println("## createBoardTest 시작 ##");
        System.out.println();
        //given
        BoardRequestDTO boardRequestDTO = new BoardRequestDTO( "title2", "content2");
        //when
        BoardResponseDTO saveBoard = boardService.saveBoard(boardRequestDTO, "aaa");
        //then
        assertEquals(saveBoard.getUserId(), "aaa");
        assertEquals(saveBoard.getTitle(), boardRequestDTO.getTitle());
        assertEquals(saveBoard.getContent(), boardRequestDTO.getContent());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updateBoardTest() {
        System.out.println("## updateBoardTest 시작 ##");
        System.out.println();

        BoardUpdateDTO boardUpdateDTO = new BoardUpdateDTO();
        boardUpdateDTO.setTitle("updateTitle1");
        boardUpdateDTO.setContent("updateContent1");

        UserEntity user = new UserEntity();
        user.setId("bbb");
        Board board = new Board(user, "update 제목", "update 내용");

        BoardResponseDTO updateBoard = boardService.updateBoard(board, boardUpdateDTO, user.getId());

        assertEquals(updateBoard.getUserId(), board.getUser().getId());
        assertEquals(updateBoard.getTitle(), boardUpdateDTO.getTitle());
        assertEquals(updateBoard.getContent(), boardUpdateDTO.getContent());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deleteBoardTest() {
        System.out.println("## deleteBoardTest 시작 ##");
        System.out.println();

        UserEntity user = new UserEntity();
        user.setId("bbb");
        Board board = new Board(user, "update 제목", "update 내용");

        BoardResponseDTO deleteBoard = boardService.deleteBoard(board, user.getId());

        assertEquals(deleteBoard.getUserId(), board.getUser().getId());
        assertEquals(deleteBoard.getTitle(), board.getTitle());
        assertEquals(deleteBoard.getContent(), board.getContent());
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    public void getOneBoardTest() {
        System.out.println("## getOneBoardTest 시작 ##");
        System.out.println();

        Long id = 12L;
        Board board = boardRepository.findById(id).orElse(null);

        BoardResponseDTO getOneBoard = boardService.getBoard(id);

        assertEquals(getOneBoard.getId(), board.getId());
        assertEquals(getOneBoard.getUserId(), board.getUser().getId());
        assertEquals(getOneBoard.getTitle(), board.getTitle());
        assertEquals(getOneBoard.getContent(), board.getContent());
    }

    @Test
    @DisplayName("게시글 전체 조회 테스트")
    public void allBoardTest() {
        System.out.println("## allBoardTest 시작 ##");
        System.out.println();

        int page = 1;
        List<BoardResponseDTO> allBoard = boardService.getAllBoard(page);

        assertEquals(allBoard.size(), 6);
    }
}
