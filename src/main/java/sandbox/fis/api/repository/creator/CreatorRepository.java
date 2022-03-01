package sandbox.fis.api.repository.creator;

import org.springframework.data.jpa.repository.JpaRepository;
import sandbox.fis.api.entity.creator.Creator;

public interface CreatorRepository extends JpaRepository<Creator, Long> {
    Boolean existsByEmail(String email);
}
