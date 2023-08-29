package me.kenux.travelog.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.kenux.travelog.domain.travellog.service.TraveLogService;
import me.kenux.travelog.domain.travellog.service.dto.request.TravelLogSaveRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TravelHistoryRestController.class)
class TravelHistoryRestControllerTest {

    @MockBean
    private TraveLogService traveLogService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void createHistoryTest() throws Exception {
        TravelLogSaveRequest request = new TravelLogSaveRequest();
        request.setTitle("title");
        request.setContents("content");
        request.setStartDate(LocalDate.of(2023, 1, 1));
        request.setDuration(3);
        ObjectMapper objectMapper = new ObjectMapper();
        final String content = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request);
        willDoNothing().given(traveLogService).saveTravelLog(any());

        final ResultActions resultActions = mockMvc.perform(
                post("/api/travels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authentication", "accessToken")
                        .content(content)
                        .with(csrf()));

        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void getHistoryByUser() throws Exception {
        final ResultActions resultActions = mockMvc.perform(get("/api/travels/{userId}", "user1")
                .header("Authentication", "accessToken"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getHistoryByUser_failed_memberNotExist() throws Exception {
        final ResultActions resultActions = mockMvc.perform(get("/api/travels/{userId}", "hacker")
                .header("Authentication", "accessToken"));

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getHistoryDetail_failed_memberNotExist() throws Exception {
        final ResultActions resultActions = mockMvc.perform(get("/api/travels/{userId}/{historyNum}", "hacker", 1)
                .header("Authentication", "accessToken"));

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getHistoryDetail_failed_historyNotExist() throws Exception {
        final ResultActions resultActions = mockMvc.perform(get("/api/travels/{userId}/{historyNum}", "user1", 0)
                .header("Authentication", "accessToken"));

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getHistoryDetail_success() throws Exception {
        final ResultActions resultActions = mockMvc.perform(get("/api/travels/{userId}/{historyNum}", "user1", 1)
                .header("Authentication", "accessToken"));

        resultActions.andExpect(status().isOk());
    }

}
