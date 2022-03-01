package sandbox.fis.api.repository.creator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sandbox.fis.api.entity.creator.Creator;

import static org.assertj.core.api.Assertions.*;

@DisplayName("CreatorRepository 테스트")
@DataJpaTest
class CreatorRepositoryTest {

    @Autowired CreatorRepository creatorRepository;

    final String email = "email@email.com";
    final String name = "creator";

    @BeforeEach
    void setUp() {
        creatorRepository.save(Creator.builder().email(email).name(name).build());
    }

    @AfterEach
    void tearDown() {
        creatorRepository.deleteAll();
    }
    
    @Test
    @DisplayName("이메일 존재시 true 반환")
    void creator_email_exists_then_return_true() {
        // when
        boolean result = creatorRepository.existsByEmail(email);

        // then
        assertThat(result).isTrue();
    }
}