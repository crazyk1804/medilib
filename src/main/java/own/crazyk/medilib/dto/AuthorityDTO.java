package own.crazyk.medilib.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter @Setter
public class AuthorityDTO implements GrantedAuthority {
	private long idx;
	private String authority;
	private String authorityName;
}
