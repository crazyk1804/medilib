package own.crazyk.medilib.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import own.crazyk.cmm.model.response.ServerResponseEntity;
import own.crazyk.cmm.util.JWTUtil;
import own.crazyk.medilib.dto.AuthCredential;
import own.crazyk.medilib.dto.AuthResponse;
import own.crazyk.medilib.dto.MemberDTO;

import java.util.HashMap;

@RestController
@AllArgsConstructor
public class MainController {
	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;

	@GetMapping("/")
	public ServerResponseEntity<String> root() {
		return ServerResponseEntity.OK("FUCK");
	}

	@PostMapping("/authenticate")
	public ServerResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthCredential credential) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						credential.getIdentity(),
						credential.getPassword()
				));

		// 응답용 사용자 객체(패스워드 삭제)
		MemberDTO principal = (MemberDTO) authentication.getPrincipal();
		MemberDTO clone = principal.toBuilder().build();
		clone.setPassword(null);

		// 토큰 생성
		HashMap<String, Object> claims = new HashMap<>();
		claims.put("identity", principal.getIdentity());
		claims.put("username", principal.getUsername());
		claims.put("authorities", principal.getAuthorities());
		String token = jwtUtil.generateToken(claims, principal);

		return ServerResponseEntity.OK(AuthResponse.builder().member(clone).token(token).build());
	}
}
