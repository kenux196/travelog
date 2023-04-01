package me.kenux.travelog.web.api.book;

import me.kenux.travelog.domain.booklog.service.BookReviewService;
import me.kenux.travelog.domain.booklog.service.dto.BookReviewResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookReviewController.class)
class BookReviewControllerTest {

    @MockBean
    BookReviewService bookReviewService;
    @Autowired
    MockMvc mockMvc;

    private static final String BASE_URL = "/api/book-review";

    @Test
    @DisplayName("GET /api/book-review?bookId=xx")
    @WithMockUser
    void getBookReviewOfSelectBook() throws Exception {
        // given
        BookReviewResponse response = Mockito.mock(BookReviewResponse.class);
        given(bookReviewService.getBookReviewWithMember(any())).willReturn(Collections.singletonList(response));

        // when
        final ResultActions resultActions = mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("bookId", anyString()));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

}