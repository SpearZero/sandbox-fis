package sandbox.fis.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sandbox.fis.api.dto.amount.AmountRequest;
import sandbox.fis.api.dto.amount.AmountResponse;
import sandbox.fis.api.service.amount.AmountService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/amounts")
public class AmountController {

    private final AmountService amountService;

    @PostMapping
    public ResponseEntity<AmountResponse> saveAmount(@Valid @RequestBody AmountRequest amountRequest) {
        return new ResponseEntity<>(amountService.saveAmount(amountRequest), HttpStatus.CREATED);
    }
}
