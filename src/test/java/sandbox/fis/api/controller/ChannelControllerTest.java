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
import sandbox.fis.api.dto.channel.EnrollChannelRequest;
import sandbox.fis.api.dto.channel.EnrollChannelResponse;
import sandbox.fis.api.service.channel.ChannelService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ChannelController 테스트")
@WebMvcTest(ChannelController.class)
class ChannelControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean ChannelService channelService;

    final Long channelId = 1L;
    final String channelName = "channel";
    final String channelEmail = "email@email.com";
    
    @Test
    @DisplayName("채널 등록시 성공 응답 반환")
    void enroll_channel_success_then_return_success_response() throws Exception {
        // given
        EnrollChannelRequest enrollChannelRequest = new EnrollChannelRequest(channelName, channelEmail);
        String body = objectMapper.writeValueAsString(enrollChannelRequest);

        // when
        when(channelService.enroll(any(EnrollChannelRequest.class))).thenReturn(new EnrollChannelResponse(channelId));

        // then
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/channels")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.channel_id").value(channelId));
    }
}