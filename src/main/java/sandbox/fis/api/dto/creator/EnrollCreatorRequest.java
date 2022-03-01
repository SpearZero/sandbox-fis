package sandbox.fis.api.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import sandbox.fis.api.entity.creator.Creator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollCreatorRequest {

    @NotBlank
    @Length(max = 18)
    private String creator_name;

    @NotBlank
    @Email
    private String creator_email;

    public Creator toEntity() {
        return Creator.builder().name(creator_name).email(creator_email).build();
    }
}
