package sandbox.fis.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sandbox.fis.api.dto.creator.EnrollCreatorRequest;
import sandbox.fis.api.dto.creator.EnrollCreatorResponse;
import sandbox.fis.api.service.creator.CreatorService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("CreatorController 테스트")
@WebMvcTest(CreatorController.class)
class CreatorControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean CreatorService creatorService;

    final Long creatorId = 1L;
    final String creatorName = "creatorName";
    final String creatorEmail = "email@email.com";

    @Test
    @DisplayName("크리에이터 등록시 성공 응답 반환")
    void enroll_creator_success_then_return_success_response() throws Exception {
        // given
        EnrollCreatorRequest enrollCreatorRequest = new EnrollCreatorRequest(creatorName, creatorEmail);
        String body = objectMapper.writeValueAsString(enrollCreatorRequest);

        // when
        when(creatorService.enroll(any(EnrollCreatorRequest.class))).thenReturn(new EnrollCreatorResponse(creatorId));

        // then
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/creators")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.creator_id").value(creatorId));
    }
}