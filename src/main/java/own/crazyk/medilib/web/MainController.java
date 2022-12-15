package own.crazyk.medilib.web;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import own.crazyk.cmm.model.response.ServerResponseEntity;

@RestController
@AllArgsConstructor
public class MainController {
	private AuthenticationManager authenticationManager;

	@GetMapping("/")
	public ServerResponseEntity<String> root() {
		return ServerResponseEntity.OK("FUCK");
	}

//	@PostMapping("/login")
//	public ServerResponseEntity
}
