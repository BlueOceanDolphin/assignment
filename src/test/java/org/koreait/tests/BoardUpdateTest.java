package org.koreait.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.commons.rests.JSONData;
import org.koreait.controllers.BoardForm;
import org.koreait.entities.BoardData;
import org.koreait.models.board.BoardSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import java.nio.charset.Charset;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class BoardUpdateTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardSaveService saveService;

    private BoardForm boardForm;

    @BeforeEach
    void init(){
        boardForm = boardForm.builder()
                .subject("제목")
                .content("내용")
                .build();
        saveService.save(boardForm);
    }



    @Test
    @DisplayName("게시글 수정 성공시 응답 코드 201")
    void test1() throws Exception {
        boardForm.setMode("update");
        boardForm.setSubject("(수정)제목");
        boardForm.setContent("(수정)내용");

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(boardForm);

        String body = mockMvc.perform(patch("/api/board/update/" + boardForm.getId())
                .contentType("application/json")
                .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));

    }
}
