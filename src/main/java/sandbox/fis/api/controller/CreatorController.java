package sandbox.fis.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sandbox.fis.api.dto.creator.EnrollCreatorRequest;
import sandbox.fis.api.dto.creator.EnrollCreatorResponse;
import sandbox.fis.api.service.creator.CreatorService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/creators")
public class CreatorController {

    private final CreatorService creatorService;

    @PostMapping
    public ResponseEntity<EnrollCreatorResponse> enroll(@Valid @RequestBody EnrollCreatorRequest enrollCreatorRequest) {
        return new ResponseEntity<>(creatorService.enroll(enrollCreatorRequest), HttpStatus.CREATED);
    }
}
