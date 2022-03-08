package sandbox.fis.api.service.creator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandbox.fis.api.dto.creator.EnrollCreatorRequest;
import sandbox.fis.api.dto.creator.EnrollCreatorResponse;
import sandbox.fis.api.entity.creator.Creator;
import sandbox.fis.api.repository.creator.CreatorRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class CreatorService {

    private final CreatorRepository creatorRepository;

    // 크리에이터 등록
    public EnrollCreatorResponse enroll(EnrollCreatorRequest request) {
        if (creatorRepository.existsByEmail(request.getCreator_email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Creator creator = creatorRepository.save(request.toEntity());

        return new EnrollCreatorResponse(creator.getId());
    }
}
