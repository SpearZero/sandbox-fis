package sandbox.fis.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sandbox.fis.api.dto.amount.AmountRequest;
import sandbox.fis.api.dto.amount.AmountResponse;
import sandbox.fis.api.dto.amount.list.CreatorAmountsDto;
import sandbox.fis.api.dto.amount.list.PerCreatorAmountsDto;
import sandbox.fis.api.service.amount.AmountQueryService;
import sandbox.fis.api.service.amount.AmountService;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/amounts")
public class AmountController {

    private final AmountService amountService;
    private final AmountQueryService amountQueryService;

    @PostMapping
    public ResponseEntity<AmountResponse> saveAmount(@Valid @RequestBody AmountRequest amountRequest) {
        return new ResponseEntity<>(amountService.saveAmount(amountRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{contractId}/creators")
    public ResponseEntity<CreatorAmountsDto> getCreatorAmounts(@PathVariable("contractId") Long contractId,
                                   @Valid @Pattern(regexp = "yyyy-mm") @RequestParam(value = "startMonth", required = true) String startMonth,
                                   @Valid @Pattern(regexp = "yyyy-mm") @RequestParam(value = "endMonth", required = true) String endMonth) {
        return new ResponseEntity<>(amountQueryService.getCreatorAmounts(contractId, startMonth, endMonth), HttpStatus.OK);
    }

    @GetMapping("/{contractId}/creators/{creatorId}")
    public ResponseEntity<PerCreatorAmountsDto> getPerCreatorAmounts(@PathVariable("contractId") Long contractId,
                                                                     @PathVariable("creatorId") Long creatorId,
                                    @Valid @Pattern(regexp = "yyyy-mm") @RequestParam(value = "startMonth", required = true) String startMonth,
                                    @Valid @Pattern(regexp = "yyyy-mm") @RequestParam(value = "endMonth", required = true) String endMonth) {
        return new ResponseEntity<>(amountQueryService.getPerCreatorAmounts(contractId, creatorId, startMonth, endMonth), HttpStatus.OK);
    }
}
