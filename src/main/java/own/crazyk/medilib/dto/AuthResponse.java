package own.crazyk.medilib.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private MemberDTO member;
    private String token;
}
