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
import sandbox.fis.api.dto.contract.ContractCompanyRequest;
import sandbox.fis.api.dto.contract.ContractCreatorRequest;
import sandbox.fis.api.dto.contract.ContractRequest;
import sandbox.fis.api.dto.contract.ContractResponse;
import sandbox.fis.api.service.contract.ContractService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ContractController 테스트")
@WebMvcTest(ContractController.class)
class ContractControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean ContractService contractService;

    final Long contractId = 1L;

    final Long channelId = 1L;
    final Integer companyRs = 50;
    final Integer creatorRs = 50;

    final Long creatorId1 = 1L;
    final Integer creatorRs1 = 50;
    final Long creatorId2 = 2L;
    final Integer creatorRs2 = 50;
    
    @Test
    @DisplayName("계약서 작성시 성공 응답 반환")
    void make_contract_success_then_return_success_response() throws Exception {
        // given
        ContractCompanyRequest contractCompanyRequest = new ContractCompanyRequest(channelId, companyRs, creatorRs);
        List<ContractCreatorRequest> contractCreatorsRequest = Stream.of(
                new ContractCreatorRequest(creatorId1, creatorRs1),
                new ContractCreatorRequest(creatorId2, creatorRs2)
        ).collect(Collectors.toList());
        ContractRequest contractRequest = new ContractRequest(contractCompanyRequest, contractCreatorsRequest);

        String body = objectMapper.writeValueAsString(contractRequest);

        // when
        when(contractService.makeContract(any(ContractRequest.class))).thenReturn(new ContractResponse(contractId));

        // then
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/contracts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contract_id").value(contractId));
    }
}