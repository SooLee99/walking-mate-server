package com.example.walkingmate_back.history.service;

import com.example.walkingmate_back.history.dto.BuyHistoryRequestDTO;
import com.example.walkingmate_back.history.dto.BuyHistoryResponseDTO;
import com.example.walkingmate_back.history.dto.CoinRequestDTO;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuyHistoryServiceTest {

    @Autowired
    BuyHistoryService buyHistoryService;

    @Autowired
    UserService userService;

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
    @DisplayName("코인 구매 저장 테스트")
    void saveBuyHistory() {
        System.out.println("## saveBuyHistory 시작 ##");
        System.out.println();

        BuyHistoryRequestDTO buyHistoryRequestDTO = new BuyHistoryRequestDTO();
        buyHistoryRequestDTO.setCoin(100);
        buyHistoryRequestDTO.setMoney(10000);
        String userId = "aaa";

        BuyHistoryResponseDTO saveBuyHistory = buyHistoryService.saveBuyHistory(buyHistoryRequestDTO, userId);

        assertEquals(saveBuyHistory.getUserId(), userId);
        assertEquals(saveBuyHistory.getCoin(), buyHistoryRequestDTO.getCoin());
        assertEquals(saveBuyHistory.getMoney(), buyHistoryRequestDTO.getMoney());
    }

    @Test
    @DisplayName("코인 구매 조회 테스트")
    void getBuyHistory() {
        System.out.println("## getBuyHistory 시작 ##");
        System.out.println();

        String userId = "aaa";

        List<BuyHistoryResponseDTO> getBuyHistory = buyHistoryService.getBuyHistory(userId);

        assertEquals(getBuyHistory.size(), 13);
    }

    @Test
    @DisplayName("코인 사용 테스트")
    void modifyBuyHistory() {
        System.out.println("## modifyBuyHistory 시작 ##");
        System.out.println();

        CoinRequestDTO coinRequestDTO = new CoinRequestDTO();
        coinRequestDTO.setCoin(100);

        UserEntity user = userService.FindUser("aaa");

        BuyHistoryResponseDTO modifyBuyHistory = buyHistoryService.modifyBuyHistory(coinRequestDTO, user);

        assertEquals(modifyBuyHistory.getUserId(), user.getId());
        assertEquals(modifyBuyHistory.getCoin(), -coinRequestDTO.getCoin());
    }
}