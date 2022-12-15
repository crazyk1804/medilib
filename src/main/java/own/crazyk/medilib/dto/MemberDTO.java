package own.crazyk.medilib.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO implements UserDetails {
	private long idx;
	private String identity;
	private String password;
	private String username;
	private List<AuthorityDTO> authorities;

	@Override
	public Collection<AuthorityDTO> getAuthorities() {
		return authorities == null ? new ArrayList<>() : authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
