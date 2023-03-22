package me.kenux.travelog.web.api.booklog;

import me.kenux.travelog.domain.booklog.service.BookLogService;
import me.kenux.travelog.domain.booklog.service.dto.BookLogResponse;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookLogController.class)
class BookLogControllerTest {

    @MockBean
    BookLogService bookLogService;

    @Autowired
    MockMvc mockMvc;

    private final String BASE_URL = "/api/book-logs";

    @Test
    @DisplayName("회원의 북로그 조회 API - 200 OK")
    @WithMockUser
    void getBookLogs_success() throws Exception {
        // given
        List<BookLogResponse> bookLogResponses = Collections.singletonList(Mockito.mock(BookLogResponse.class));
        given(bookLogService.getBookLogs(any())).willReturn(bookLogResponses);

        // when
        final ResultActions actions = mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("memberId", anyString()));

        // then
        actions.andDo(print())
                .andExpect(status().isOk());
    }
}