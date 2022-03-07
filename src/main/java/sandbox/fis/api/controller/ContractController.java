package sandbox.fis.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sandbox.fis.api.dto.contract.ContractRequest;
import sandbox.fis.api.dto.contract.ContractResponse;
import sandbox.fis.api.service.contract.ContractService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController {

    private final ContractService contractService;

    // 계약서 등록
    @PostMapping
    public ResponseEntity<ContractResponse> makeContract(@Valid @RequestBody ContractRequest contractRequest) {
        return new ResponseEntity<>(contractService.makeContract(contractRequest), HttpStatus.CREATED);
    }
}
