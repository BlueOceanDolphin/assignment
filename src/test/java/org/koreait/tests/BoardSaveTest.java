package org.koreait.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.commons.rests.JSONData;
import org.koreait.controllers.BoardForm;
import org.koreait.models.board.BoardSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class BoardSaveTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardSaveService saveService;

    @Test
    @DisplayName("작성 성공시 상태코드 201로 응답")
    void Test1() throws Exception {

        BoardForm boardForm = BoardForm.builder()
                                .subject("제목")
                                .content("내용")
                                .build();

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(boardForm);

        mockMvc.perform(post("/api/board")
                .contentType("application/json")
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("유효성 검사 - 제목 누락시 응답 코드는 400, message에 제목 포함")
    void Test2() throws Exception {
        BoardForm boardForm = BoardForm.builder()
                        .content("내용").build();

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(boardForm);

        String body = mockMvc.perform(post("/api/board")
                    .contentType("application/json")
                    .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse()
                    .getContentAsString(Charset.forName("UTF-8"));

        JSONData<Object> result = om.readValue(body, JSONData.class);

        assertTrue(result.getMessage().contains("제목"));
    }
}
