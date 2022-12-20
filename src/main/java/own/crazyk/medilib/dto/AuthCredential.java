package own.crazyk.medilib.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCredential {
    @NotEmpty
    private String identity;
    @NotEmpty
    private String password;
}
