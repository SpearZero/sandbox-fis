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
import sandbox.fis.api.dto.amount.AmountRequest;
import sandbox.fis.api.dto.amount.AmountResponse;
import sandbox.fis.api.service.amount.AmountService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AmountController 테스트")
@WebMvcTest(AmountController.class)
class AmountControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean AmountService amountService;

    final Long contractId = 1L;
    final LocalDate day = LocalDate.of(2022, 3, 7);
    final BigDecimal amount = new BigDecimal("12393.15");
    
    @Test
    @DisplayName("금액 등록시 성공 응답 반환")
    void save_amount_success_then_return_success_response() throws Exception {
        // given
        AmountRequest amountRequest = new AmountRequest(contractId, day, amount);
        String body = objectMapper.writeValueAsString(amountRequest);

        // when
        when(amountService.saveAmount(any(AmountRequest.class))).thenReturn(new AmountResponse(contractId));

        // then
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/amounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contract_id").value(contractId));
    }
}