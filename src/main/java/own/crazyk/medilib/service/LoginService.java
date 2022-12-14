package own.crazyk.medilib.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import own.crazyk.medilib.domain.Member;
import own.crazyk.medilib.dto.MemberDTO;
import own.crazyk.medilib.repositories.MemberRepository;

@AllArgsConstructor
@Service
public class LoginService implements UserDetailsService {
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String identity) throws UsernameNotFoundException {
		Member member = memberRepository.findByIdentity(identity)
			.orElseThrow(() -> new UsernameNotFoundException(identity));
		return MemberDTO.builder()
			.idx(member.getIdx())
			.identity(member.getIdentity())
			.password(member.getPassword())
			.username(member.getUsername())
			.build();
	}

//	public void authenticate(String identity, String password) {
//		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(identity, password));
//	}
}
