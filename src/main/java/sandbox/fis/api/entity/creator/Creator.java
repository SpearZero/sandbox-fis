package sandbox.fis.api.entity.creator;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id")
    private Long id;

    @Column(length = 54, nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Builder
    public Creator(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
