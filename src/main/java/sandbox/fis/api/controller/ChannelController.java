package sandbox.fis.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sandbox.fis.api.dto.channel.EnrollChannelRequest;
import sandbox.fis.api.dto.channel.EnrollChannelResponse;
import sandbox.fis.api.service.channel.ChannelService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/channels")
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping
    public ResponseEntity<EnrollChannelResponse> enroll(@Valid @RequestBody EnrollChannelRequest enrollChannelRequest) {
        return new ResponseEntity<>(channelService.enroll(enrollChannelRequest), HttpStatus.CREATED);
    }
}
