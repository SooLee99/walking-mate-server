package com.example.walkingmate_back.history.service;

import com.example.walkingmate_back.user.dto.UserBodyResponseDTO;
import com.example.walkingmate_back.user.dto.UserBodyUpdateDTO;
import com.example.walkingmate_back.user.entity.UserBody;
import com.example.walkingmate_back.user.service.UserBodyService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserBodyServiceTest {

    @Autowired
    private UserBodyService userBodyService;

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
    @DisplayName("사용자 신체정보 조회 테스트")
    void getUserBodyTest() {
        System.out.println("## getUserBodyTest 시작 ##");
        System.out.println();

        UserBodyResponseDTO userBodyResponseDTO = userBodyService.getUserBody("aaa");

        assertEquals(userBodyResponseDTO.getUserId(), "aaa");
        assertEquals(userBodyResponseDTO.getWeight(), 50);
        assertEquals(userBodyResponseDTO.getHeight(), 160);
    }

    @Test
    @DisplayName("사용자 신체정보 수정 테스트")
    void updateUserBodyTest() {
        System.out.println("## updateUserBodyTest 시작 ##");
        System.out.println();

        UserBodyUpdateDTO userBodyUpdateDTO = new UserBodyUpdateDTO();
        userBodyUpdateDTO.setHeight(170);
        userBodyUpdateDTO.setWeight(60);

        UserBodyResponseDTO userBodyResponseDTO = userBodyService.updateUserBody(userBodyUpdateDTO, "aaa");

        assertEquals(userBodyResponseDTO.getUserId(), "aaa");
        assertEquals(userBodyResponseDTO.getHeight(), userBodyUpdateDTO.getHeight());
        assertEquals(userBodyResponseDTO.getWeight(), userBodyUpdateDTO.getWeight());
    }
}