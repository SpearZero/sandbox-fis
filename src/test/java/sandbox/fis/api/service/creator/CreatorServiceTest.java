package sandbox.fis.api.service.creator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import sandbox.fis.api.dto.creator.EnrollCreatorRequest;
import sandbox.fis.api.dto.creator.EnrollCreatorResponse;
import sandbox.fis.api.entity.creator.Creator;
import sandbox.fis.api.repository.creator.CreatorRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("CreatorService 테스트")
@ExtendWith(MockitoExtension.class)
class CreatorServiceTest {

    @InjectMocks CreatorService creatorService;
    @Mock CreatorRepository creatorRepository;

    // Creator 정보
    Creator creator;
    final Long creatorId = 1L;
    final String creatorName = "creator";
    final String creatorEmail = "email@email.com";

    @BeforeEach
    void setUp() {
        creator = Creator.builder().email(creatorEmail).name(creatorName).build();
        ReflectionTestUtils.setField(creator, "id", creatorId);
    }
    
    @Test
    @DisplayName("크리에이터 등록시 이메일이 중복되면 IllegalArgumentException 발생")
    void enroll_creator_email_exists_then_throw_IllegalArgumentException() {
        // given
        EnrollCreatorRequest enrollCreatorRequest = new EnrollCreatorRequest(creatorName, creatorEmail);

        // when
        when(creatorRepository.existsByEmail(creatorEmail)).thenReturn(true);

        // then
        assertThrows(IllegalArgumentException.class, () -> creatorService.enroll(enrollCreatorRequest));
    }
    
    @Test
    @DisplayName("크리에이터 등록시 등록 성공")
    void enroll_creator_then_enroll_creator_success() {
        // given
        EnrollCreatorRequest enrollCreatorRequest = new EnrollCreatorRequest(creatorName, creatorEmail);

        // when
        when(creatorRepository.existsByEmail(creatorEmail)).thenReturn(false);
        when(creatorRepository.save(any(Creator.class))).thenReturn(creator);
        EnrollCreatorResponse enrollCreatorResponse = creatorService.enroll(enrollCreatorRequest);

        // then
        assertThat(enrollCreatorResponse.getCreator_id()).isEqualTo(creatorId);
    }
}