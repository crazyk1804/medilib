package own.crazyk.medilib.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import own.crazyk.cmm.model.response.ServerResponseEntity;

@RestController
public class MainController {

	@GetMapping("/")
	public ServerResponseEntity<String> root() {
		return ServerResponseEntity.OK("FUCK");
	}
}
