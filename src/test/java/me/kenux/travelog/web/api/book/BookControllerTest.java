package me.kenux.travelog.web.api.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kenux.travelog.domain.booklog.service.BookManagementService;
import me.kenux.travelog.domain.booklog.service.BookSearchService;
import me.kenux.travelog.domain.booklog.service.dto.AddBookRequest;
import me.kenux.travelog.domain.booklog.service.dto.BookInfoDto;
import me.kenux.travelog.global.security.jwt.JwtTokenIssuer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenIssuer jwtTokenIssuer;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private BookManagementService bookManagementService;
    @MockBean
    private BookSearchService bookSearchService;

    @Test
    @DisplayName("Book List 가져온다.")
    @WithMockUser(roles = "USER", username = "user@test.com")
    void getBooks_success() throws Exception {
        // given
        List<BookInfoDto.Basic> mockBookInfos = new ArrayList<>();
        given(bookSearchService.getBooks(any())).willReturn(mockBookInfos);

        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("책을 등록한다.")
    @WithMockUser(roles = "USER", username = "user@test.com")
    void registerBook() throws Exception {
        // given
        AddBookRequest request = Mockito.mock(AddBookRequest.class);

        ObjectMapper objectMapper = new ObjectMapper();
        final String content = objectMapper.writeValueAsString(request);
        willDoNothing().given(bookManagementService).addBook(any());

        // when
        final ResultActions actions = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf()));

        // then
        actions.andExpect(status().isNoContent())
                .andDo(print());
    }
}
