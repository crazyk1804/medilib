package own.crazyk.medilib.web;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import own.crazyk.cmm.model.response.ServerResponseEntity;
import own.crazyk.cmm.util.JWTUtil;
import own.crazyk.medilib.dto.MemberDTO;

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
	public ServerResponseEntity<String> authenticate(@RequestBody MemberDTO credential) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						credential.getIdentity(),
						credential.getPassword()
				));
		MemberDTO principal = (MemberDTO) authentication.getPrincipal();
		return ServerResponseEntity.OK(jwtUtil.generateToken(principal));
	}
}
