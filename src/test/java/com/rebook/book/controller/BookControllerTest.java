package com.rebook.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebook.book.controller.request.BookCreateRequest;
import com.rebook.book.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

//    @DisplayName("새로운 책을 저장한다.")
//    @Test
//    void saveBook() throws Exception {
//        // given
//        BookCreateRequest request = BookCreateRequest.builder()
//                .title("제목")
//                .author("저자")
//                .thumbnailUrl("책 표지")
//                .hashtagIds(List.of(1L, 2L))
//                .build();
//
//        when(bookService.save(any(BookCreateCommand.class)))
//                .thenReturn(any(BookDto.class));
//
//        // when, then
//        mockMvc.perform( //todo: 문제 발생하는 지점
//                        post("/api/v1/books")
//                                .content(objectMapper.writeValueAsString(request))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(header().string(LOCATION, "books/1"));
//    }

    @DisplayName("책 제목을 입력하지 않으면 에러가 발생한다.")
    @Test
    void saveBookWithOutTitle() throws Exception {
        // given
        BookCreateRequest badRequest = BookCreateRequest.builder()
                .title(null)
                .author("저자")
                .thumbnailUrl("책 표지")
                .hashtagIds(List.of(1L, 2L))
                .build();

        // when, then
        mockMvc.perform(
                        post("/api/v1/books")
                                .content(objectMapper.writeValueAsString(badRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("책 제목을 입력해주세요."));
    }
}